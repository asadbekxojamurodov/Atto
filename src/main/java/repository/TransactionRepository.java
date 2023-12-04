package repository;

import db.DatabaseUtil;
import dto.CardDto;
import dto.ProfileDto;
import dto.TransactionDto;
import enums.Status;
import enums.TransactionType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {
    public int createTransaction(TransactionDto transaction) {

        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "insert into transaction(card_id,card_number,terminal_code,amount,transaction_type,created_date,phone) values(?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, transaction.getCardId());
            statement.setString(2, transaction.getCardNumber());
            if (transaction.getTerminalCode() != null) {
                statement.setString(3, transaction.getTerminalCode());
            } else {
                statement.setObject(3, null);
            }
            statement.setDouble(4, transaction.getAmount());
            statement.setString(5, transaction.getTransactionType().name());
            statement.setTimestamp(6, Timestamp.valueOf(transaction.getCreatedDate()));
            statement.setString(7, transaction.getPhone());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<TransactionDto> getAllTransaction() {

        List<TransactionDto> transactionList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from transaction";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDto transaction = new TransactionDto();
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setCardNumber(resultSet.getString("card_number"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalCode(resultSet.getString("terminal_code"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                transactionList.add(transaction);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionList;

    }

    public List<TransactionDto> userTransaction(String phone) {
        List<TransactionDto> transactionList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from transaction where phone = '" + phone+ "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TransactionDto transaction = new TransactionDto();
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setCardNumber(resultSet.getString("card_number"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalCode(resultSet.getString("terminal_code"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                transactionList.add(transaction);
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionList;

    }

    public CardDto getCardIdByPhone(String phone) {

        return null;
    }

    public TransactionDto isExistTransaction() {

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from transaction";
            ResultSet resultSet = statement.executeQuery(sql);
            TransactionDto transaction = new TransactionDto();

            if (resultSet.next()) {
                transaction.setId(resultSet.getInt("id"));
                return transaction;
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public CardDto getCompanyCardBalance() {
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from card where card_number = '"+"5555"+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            CardDto card = new CardDto();

            if (resultSet.next()) {

                card.setCardNumber(resultSet.getString("card_number"));
                card.setBalance(resultSet.getDouble("balance"));
                card.setExpireDate(resultSet.getDate("expire_date").toLocalDate());
                card.setStatus(Status.valueOf(resultSet.getString("status")));
                card.setPhone(resultSet.getString("phone"));
                card.setCreatedDate(Timestamp.valueOf(resultSet.getString("created_date")).toLocalDateTime());
                return card;
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }
}
