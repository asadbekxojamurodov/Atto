package repository;

import db.DatabaseUtil;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;

import java.sql.*;


public class ProfileRepository {

    int effectedRows;

    public ProfileDto login(ProfileDto profile) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "select * from profile where phone = ? and password = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, profile.getPhone());
            preparedStatement.setString(2, profile.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                profile.setName(resultSet.getString("name"));
                profile.setSurname(resultSet.getString("surname"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setPassword(resultSet.getString("password"));
                profile.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                profile.setStatus(Status.valueOf(resultSet.getString("status")));
                profile.setProfileRole(ProfileRole.valueOf(resultSet.getString("profile_role")));
                return profile;
            }

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean registration(ProfileDto profile) {

        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "insert into profile(name,surname,phone,password,created_date,status,profile_role) values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getSurname());
            preparedStatement.setString(3, profile.getPhone());
            preparedStatement.setString(4, profile.getPassword());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(profile.getCreatedDate()));
            preparedStatement.setString(6, profile.getStatus().name());
            preparedStatement.setString(7, profile.getProfileRole().name());

            effectedRows = preparedStatement.executeUpdate();
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
