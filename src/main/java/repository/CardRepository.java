package repository;

import db.DatabaseUtil;
import dto.CardDto;
import enums.Status;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class CardRepository {

    int res;

    public void createCard(CardDto card) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "insert into card(card_number,expire_date,balance,status,created_date) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpireDate()));
            preparedStatement.setDouble(3, card.getBalance());
            preparedStatement.setString(4, card.getStatus().name());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(card.getCreatedDate()));

            preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CardDto> getAllNoActiveCardList() {
        List<CardDto> cardList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();

            String sql = "select * from card where status = '" + "NO_ACTIVE" + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                CardDto cardDto = new CardDto();
                cardDto.setCardNumber(resultSet.getString("card_number"));
                cardDto.setExpireDate(resultSet.getDate("expire_date").toLocalDate());
                cardDto.setBalance(resultSet.getDouble("balance"));
                cardDto.setStatus(Status.valueOf(resultSet.getString("status")));
                cardDto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                cardList.add(cardDto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardList;
    }

    public List<CardDto> getAllCard() {
        List<CardDto> cardList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from card";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                CardDto cardDto = new CardDto();
                cardDto.setId(resultSet.getInt("id"));
                cardDto.setCardNumber(resultSet.getString("card_number"));
                cardDto.setBalance(resultSet.getDouble("balance"));
                cardDto.setExpireDate(resultSet.getDate("expire_date").toLocalDate());
                cardDto.setStatus(Status.valueOf(resultSet.getString("status")));
                cardDto.setVisible(resultSet.getBoolean("visible"));
                cardDto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                cardList.add(cardDto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardList;
    }

    public CardDto getCardByNumber(String cardNumber) {

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from card where card_number = '" + cardNumber + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Integer cardId = resultSet.getInt("id");
                String number = resultSet.getString("card_number");
                Double balance = resultSet.getDouble("balance");
                LocalDate expDate = resultSet.getDate("expire_date").toLocalDate();
                String status = resultSet.getString("status");
                String phone = resultSet.getString("phone");
                LocalDateTime createdDate = resultSet.getTimestamp("created_date").toLocalDateTime();

                CardDto card = new CardDto();
                card.setId(cardId);
                card.setCardNumber(number);
                card.setBalance(balance);
                card.setExpireDate(expDate);
                card.setStatus(Status.valueOf(status));
                card.setPhone(phone);
                card.setCreatedDate(createdDate);

                return card;
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addCard(CardDto cardDto) {

        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "update card set phone = ?,status = ? where card_number = '" + cardDto.getCardNumber() + "' ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, cardDto.getPhone());
            preparedStatement.setString(2, cardDto.getStatus().name());

            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CardDto> getAllMyCard(String phone) {

        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "select * from card where visible = true and phone = '" + phone + "';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<CardDto> cardList = new LinkedList<>();

            while (resultSet.next()) {

                CardDto card = new CardDto();
                card.setId(resultSet.getInt("id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBalance(resultSet.getDouble("balance"));
                card.setExpireDate(resultSet.getDate("expire_date").toLocalDate());
                card.setStatus(Status.valueOf(resultSet.getString("status")));
                card.setVisible(resultSet.getBoolean("visible"));
                card.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                card.setPhone(resultSet.getString("phone"));

                cardList.add(card);
            }
            return cardList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;

    }

    public boolean changeCardStatus(CardDto cardDto) {
        return updateStatus(cardDto);

    }

    public boolean updateStatus(CardDto cardDto) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set status = ? where card_number = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(cardDto.getStatus()));
            preparedStatement.setString(2, cardDto.getCardNumber());

            res = preparedStatement.executeUpdate();
            con.close();
            return res != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refill(String cardNumber, double balance) {

        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "update card set balance = balance + ? where card_number = '" + cardNumber + "'";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, balance);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int AdminUpdateCard(CardDto card) {
        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "update card set  expire_date = ? where card_number = '" + card.getCardNumber() + "'";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(card.getExpireDate()));

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int updateCardStatus(String cardNumber, Status status) {

        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "update card set  status = ? where card_number = '" + cardNumber + "'";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(status));
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int adminDeleteCard(String cardNumber) {
        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "update card set  visible = ? where card_number = '" + cardNumber + "'";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void profileDeleteCard(CardDto exist) {
        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "update card set  visible = ? where card_number = '" + exist.getCardNumber() + "'";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveCompanyCard(CardDto card) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "insert into card(card_number,expire_date,balance,status,created_date,phone) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpireDate()));
            preparedStatement.setDouble(3, card.getBalance());
            preparedStatement.setString(4, card.getStatus().name());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(card.getCreatedDate()));
            preparedStatement.setString(6, card.getPhone());

            preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getMyBalanceByCardNumber(String cardNumber) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "select * from card where visible = true and card_number = '" + cardNumber + "';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Double balance;

            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
                return balance;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0d;
    }
}

