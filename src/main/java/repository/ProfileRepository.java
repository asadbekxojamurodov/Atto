package repository;

import db.DatabaseUtil;
import dto.CardDto;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;

import java.sql.*;
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

    public List<ProfileDto> getAllProfile(){
        List<ProfileDto> profileDtoList = new LinkedList<>();

        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from profile";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ProfileDto profileDto = new ProfileDto();
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

    public List<ProfileDto> getAllProfileById(ProfileDto profile){
        List<ProfileDto> profileDtoList = new LinkedList<>();
         int id = profile.getId();
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "select * from profile where id = "+id;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ProfileDto profileDto = new ProfileDto();
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
}
