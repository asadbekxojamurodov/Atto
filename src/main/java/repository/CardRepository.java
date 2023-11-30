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
            String sql = "insert into card(card_number,balance,status,created_date) values(?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDouble(2, card.getBalance());
            preparedStatement.setString(3, card.getStatus().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(card.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    public boolean addCard(CardDto cardDto) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update card set phone = ?,expire_date = ?,status = ?  ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, cardDto.getPhone());
            preparedStatement.setDate(2, Date.valueOf(cardDto.getExpireDate()));
            preparedStatement.setString(3, cardDto.getStatus().name());

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
            String sql = "update card set status = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, dto.getStatus().name());

            res = preparedStatement.executeUpdate();
            con.close();
            return res == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

