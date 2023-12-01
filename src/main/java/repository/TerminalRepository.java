package repository;

import db.DatabaseUtil;
import dto.TerminalDto;

import java.sql.*;

public class TerminalRepository {
    public boolean createTerminal(TerminalDto terminalDto) {
        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "insert into Terminal(code,address,status,created_date) values(?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, terminalDto.getCode());
            preparedStatement.setString(2, terminalDto.getAddress());
            preparedStatement.setString(3, terminalDto.getStatusTerminal().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(terminalDto.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
