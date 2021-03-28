package ru.netology.web.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static ru.netology.web.data.DataHelper.*;

public class DataBase {
    private DataBase() {}

    public static List<UserInfo> getUsers() {
        List<UserInfo> users = new ArrayList<>();
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT * FROM users;";

      try (
              val conn = DriverManager.getConnection(
                      "jdbc:mysql://localhost:3306/app", "app", "pass");

      )
      {
          users = runner.query(conn, dataSQL, new BeanListHandler<>(UserInfo.class));

      }
        catch (SQLException e) {
          e.printStackTrace();
        }
        return users;
      }

    public static UserInfo getUser(String login) {
        UserInfo user = new UserInfo();
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT * FROM users where login = ?;";
        ResultSetHandler<UserInfo>  userInfoResultSetHandler = new BeanHandler<UserInfo>(UserInfo.class);
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");

        )
        {
            user =  runner.query(conn, dataSQL, userInfoResultSetHandler, login);

        }
        catch (SQLException e) {
            System.out.println(e);
        }
       return user;
    }

    public static VerificationCode getAuthCode(String userId) {
        VerificationCode code = new VerificationCode();
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT * FROM auth_codes where user_id = ? ORDER BY created desc;";
        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

                )
        {
            code =  runner.query(conn, dataSQL, new BeanHandler<>(VerificationCode.class), userId);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return code;
    }


    public static List<CardInfo> returnCardsForUser(String userId) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String querySQL = "SELECT id, number, ROUND (balance_in_kopecks / 100) as balance FROM cards WHERE user_id = ? ORDER BY number;";
        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        )
        {
        return runner.query(conn, querySQL, new BeanListHandler<>(CardInfo.class), userId);
        }
    }

    public static CardInfo returnCardInfo(String cardNumber) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String querySQL = "SELECT id, number, ROUND (balance_in_kopecks / 100) as balance FROM cards WHERE number = ?;";
        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        )
        {
            return runner.query(conn, querySQL, new BeanHandler<>(CardInfo.class), cardNumber);
        }
    }


    public static void cleanAll() {

        QueryRunner runner = new QueryRunner();
        String clearUser = "DELETE FROM users";
        String clearCode = "DELETE FROM auth_codes;";
        String clearCard = "DELETE FROM cards;";
        String clearTransactions = "DELETE FROM card_transactions;";

        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

        )
        {
            runner.update(conn, clearTransactions);
            runner.update(conn, clearCard);
            runner.update(conn, clearCode);
            runner.update(conn, clearUser);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void cleanCode() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String clearCode = "DELETE FROM auth_codes;";

        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        )
        {
            runner.update(conn, clearCode);
        }
    }

}
