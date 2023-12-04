package repository;

import db.DatabaseUtil;
import dto.TerminalDto;
import enums.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class TerminalRepository {
    int res;


    public List<TerminalDto> getAllTerminal() {
        List<TerminalDto> terminalList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from terminal where visible = true";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TerminalDto terminal = new TerminalDto();
                terminal.setId(resultSet.getInt("id"));
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("address"));
                terminal.setStatusTerminal(Status.valueOf(resultSet.getString("status")));
                terminal.setVisible(resultSet.getBoolean("visible"));
                terminal.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                terminalList.add(terminal);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return terminalList;
    }

    public void updateTerminal(TerminalDto terminal) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update terminal set address = ? where code = '" + terminal.getCode() + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, terminal.getAddress());

            res = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changeTerminalStatus(String code, Status status) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update terminal set status = ? where code = '" + code + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(Status.valueOf(status.name())));

            res = preparedStatement.executeUpdate();
            con.close();
            return res != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(TerminalDto terminal) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update terminal set visible = false where code = '" + terminal.getCode() + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            res = preparedStatement.executeUpdate();
            con.close();
            return res != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public TerminalDto getTerminalByCode(String code) {


        try (Connection connection = DatabaseUtil.getConnection()) {

            String sql = "select * from terminal where visible = true and code=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TerminalDto terminal = new TerminalDto();
                terminal.setId(resultSet.getInt("id"));
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("address"));
                terminal.setVisible(resultSet.getBoolean("visible"));
                terminal.setStatusTerminal(Status.valueOf(resultSet.getString("status")));
                terminal.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                return terminal;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public int adminCreateTerminal(TerminalDto terminal, boolean visible) {
        Connection con = DatabaseUtil.getConnection();
        try {
            String sql = "insert into terminal(code,address,status,created_date,visible) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, terminal.getCode());
            preparedStatement.setString(2, terminal.getAddress());
            preparedStatement.setString(3, terminal.getStatusTerminal().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(terminal.getCreatedDate()));
            preparedStatement.setBoolean(5, visible);

            int effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int createDefaultTerminal() {
        Connection con = DatabaseUtil.getConnection();
        try {
            String sql = "insert into terminal(code,address,status,created_date,visible) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "toshkent");
            preparedStatement.setString(3, String.valueOf(Status.ACTIVE));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setBoolean(5, true);

            int effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    public void makePayment(Double amount, String cardNumber) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set balance = balance - ? where card_number = '" + cardNumber + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setDouble(1, amount);

            res = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void addBalanceToCompanyCard(String amount) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set balance = balance + ? where card_number = '" + "5555" + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setDouble(1, Double.parseDouble(amount));

            res = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
