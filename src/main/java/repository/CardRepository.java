package repository;

import db.DatabaseUtil;
import dto.CardDto;
import enums.Status;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CardRepository {

    int res;

    public boolean createCard(CardDto card) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "insert into card(card_number,expire_date,balance,status,created_date) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpireDate()));
            preparedStatement.setDouble(3, card.getBalance());
            preparedStatement.setString(4, card.getStatus().name());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(card.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows != 0;
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
                cardDto.setCardNumber(resultSet.getString("card_number"));
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

//    public List<CardDto> getAllCardById() {
//        List<CardDto> cardList = new LinkedList<>();
//
//        try {
//            Connection con = DatabaseUtil.getConnection();
//            Statement statement = con.createStatement();
//            String sql = "select * from card";
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                CardDto cardDto = new CardDto();
//                cardDto.setCardNumber(resultSet.getString("card_number"));
//                cardDto.setBalance(resultSet.getDouble("balance"));
//                cardDto.setStatus(Status.valueOf(resultSet.getString("status")));
//                cardDto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
//
//                cardList.add(cardDto);
//            }
//            con.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return cardList;
//    }

    public boolean addCard(CardDto cardDto, String cardNumber) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set phone = ?,status = ? where card_number = ? ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, cardDto.getPhone());
            preparedStatement.setString(2, cardDto.getStatus().name());
            preparedStatement.setString(3, cardNumber);

            int i = preparedStatement.executeUpdate();
            con.close();
            return i != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(CardDto cardDto) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set expire_date = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(cardDto.getExpireDate()));

            res = preparedStatement.executeUpdate();
            con.close();
            return res != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteCard(CardDto dto) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set status = ? where card_number = '" + dto.getCardNumber() + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, dto.getStatus().name());

            res = preparedStatement.executeUpdate();
            con.close();
            return res != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<CardDto> getAllMyCard(String phone) {
        List<CardDto> myCardList = new LinkedList<>();
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from card where phone = '" + phone + "' and  status = '" + "ACTIVE" + "' or  status = '" + "WAITED" + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                CardDto cardDto = new CardDto();
                cardDto.setCardNumber(resultSet.getString("card_number"));
                cardDto.setExpireDate(resultSet.getDate("expire_date").toLocalDate());
                cardDto.setBalance(resultSet.getDouble("balance"));
                cardDto.setStatus(Status.valueOf(resultSet.getString("status")));
                cardDto.setPhone(resultSet.getString("phone"));
                cardDto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                myCardList.add(cardDto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myCardList;

    }

    public boolean changeCardStatus(String cardNumber) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set status = ? where card_number = '" + cardNumber + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, "WAITED");

            res = preparedStatement.executeUpdate();
            con.close();
            return res != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

