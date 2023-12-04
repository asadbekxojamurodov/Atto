package repository;

import db.DatabaseUtil;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


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

    public Integer registration(ProfileDto profile) {

        Connection con = DatabaseUtil.getConnection();
        try {
            String sql = "insert into profile(name,surname,phone,password,created_date,status,profile_role) values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getSurname());
            preparedStatement.setString(3, profile.getPhone());
            preparedStatement.setString(4, profile.getPassword());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, profile.getStatus().name());
            preparedStatement.setString(7, profile.getProfileRole().name());

            effectedRows = preparedStatement.executeUpdate();
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
                System.exit(-1);
            }

        }
        return 0;

    }

    public List<ProfileDto> getAllProfile() {
        List<ProfileDto> profileDtoList = new LinkedList<>();
        ProfileDto profileDto;
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from profile";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                profileDto = new ProfileDto();
                profileDto.setId(resultSet.getInt("id"));
                profileDto.setName(resultSet.getString("name"));
                profileDto.setSurname(resultSet.getString("surname"));
                profileDto.setPhone(resultSet.getString("phone"));
                profileDto.setPassword(resultSet.getString("password"));
                profileDto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                profileDto.setStatus(Status.valueOf(resultSet.getString("status")));
                profileDto.setProfileRole(ProfileRole.valueOf(resultSet.getString("profile_role")));

                profileDtoList.add(profileDto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return profileDtoList;

    }

    public void changeProfileStatus(ProfileDto profile, Status status) {
        try {
            Connection con = DatabaseUtil.getConnection();
            String sql = "update profile set status = ? where phone = '" + profile.getPhone() + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(status));

            effectedRows = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProfileDto getProfileByPhoneAndPassword(String phone, String password) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = String.format("Select  * from Profile where phone= '%s' and password = '%s' ;", phone, password);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ProfileDto profile = new ProfileDto();
                profile.setId(resultSet.getInt("id"));
                profile.setName(resultSet.getString("name"));
                profile.setSurname(resultSet.getString("surname"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setPassword(resultSet.getString("password"));
                profile.setProfileRole(ProfileRole.valueOf(resultSet.getString("profile_role")));
                profile.setStatus(Status.valueOf(resultSet.getString("status")));
                return profile;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return null;
    }

    public ProfileDto getProfileByPhone(String phoneNumber) {
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from profile where phone = '" + phoneNumber + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                LocalDateTime createdDate = resultSet.getTimestamp("created_date").toLocalDateTime();
                Status status = Status.valueOf(resultSet.getString("status"));
                ProfileRole profileRole = ProfileRole.valueOf(resultSet.getString("profile_role"));

                ProfileDto profile = new ProfileDto();
                profile.setId(id);
                profile.setName(name);
                profile.setSurname(surname);
                profile.setPhone(phone);
                profile.setPassword(password);
                profile.setCreatedDate(createdDate);
                profile.setStatus(status);
                profile.setProfileRole(profileRole);

                return profile;
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

}
