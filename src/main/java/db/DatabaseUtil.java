package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_lesson_jon",
                    "asadbek", "1");
            return con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createProfileTable(){
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "  create table if not exists Profile(" +
                    "        id serial primary key," +
                    "        name varchar not null," +
                    "        surname varchar not null," +
                    "        phone varchar(13) primary key," +
                    "        password varchar not null," +
                    "        created_date timestamp default now()," +
                    "        status varchar default  'ACTIVE'," +
                    "        profile_role varchar not null );";
            statement.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCardTable(){
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "   create table if not exists card(" +
                    "        id serial," +
                    "        card_number varchar(16) primary key," +
                    "        expire_date date," +
                    "        balance double precision," +
                    "        status varchar default  'NO_ACTIVE'," +
                    "        phone varchar(13) references profile (phone)," +
                    "        created_date timestamp default now()" +
                    "      );";
            statement.executeUpdate(sql);
         con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTerminalTable(){
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "   create table if not exists terminal(" +
                    "        id serial," +
                    "        code varchar  primary key," +
                    "        address varchar not null," +
                    "        status varchar default  'ACTIVE'," +
                    "        created_date timestamp default now()" +
                    "      );";
            statement.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTransactionTable(){
        try {
            Connection con = DatabaseUtil.getConnection();
            Statement statement = con.createStatement();
            String sql = "   create table if not exists Transaction(" +
                    "        id serial," +
                    "        card_number varchar(16)  references card(card_number)," +
                    "        amount double precision, " +
                    "        terminal_code varchar references terminal(code)," +
                    "        transaction_type varchar not null, " +
                    "        created_date timestamp default now()" +
                    "      );";
            statement.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

