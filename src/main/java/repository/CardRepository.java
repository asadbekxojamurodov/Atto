package repository;

import db.DatabaseUtil;
import dto.CardDto;
import enums.Status;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CardRepository {

    CardDto cardDto = new CardDto();

    public boolean createCard(CardDto card) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "insert into card(card_number,balance,status,phone,created_date) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, card.getCardNumber());
//            preparedStatement.setDate(2, Date.valueOf(card.getExpireDate()));
            preparedStatement.setDouble(2, card.getBalance());
            preparedStatement.setString(3, card.getStatus().name());
            preparedStatement.setString(4, card.getPhone());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(card.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<CardDto> getAllCard() {
        List<CardDto> dtoList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from card ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
//                if (resultSet.getString("phone") != null) {
//                    cardDto.setPhone(resultSet.getString("phone"));
//                }
//                if (Objects.equals(resultSet.getDate("expire_date").toString(),null) ) {
//                    cardDto.setExpireDate(resultSet.getDate("expire_date").toLocalDate());
//                }
                cardDto.setCardNumber(resultSet.getString("card_number"));
                cardDto.setBalance(resultSet.getDouble("balance"));
                cardDto.setStatus(Status.valueOf(resultSet.getString("status")));

                cardDto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                dtoList.add(cardDto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }
}
