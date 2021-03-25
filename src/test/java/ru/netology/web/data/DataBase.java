package ru.netology.web.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.netology.web.data.DataHelper.*;

public class DataBase {
    private DataBase() {}

    public static List<UserInfo> getUsers() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT * FROM users;";

      try (
              val conn = DriverManager.getConnection(
                      "jdbc:mysql://localhost:3306/app", "app", "pass");

      )
      {
          List<UserInfo> users = runner.query(conn, dataSQL, new BeanListHandler<>(UserInfo.class));
          return users;
      }

      }

    public static UserInfo getUser(String login) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT * FROM users where login = ?;";
        ResultSetHandler<UserInfo>  userInfoResultSetHandler = new BeanHandler<UserInfo>(UserInfo.class);
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");

        )
        {
            UserInfo user = runner.query(conn, dataSQL, userInfoResultSetHandler, login);
            return user;
        }

    }

    public static VerificationCode getAuthCode(String userId) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String dataSQL = "SELECT * FROM auth_codes where user_id = ?;";
        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

                )
        {
            return runner.query(conn, dataSQL, new BeanHandler<>(VerificationCode.class), userId);
        }

    }

    public static void cleanAll() throws SQLException {
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

    }


}
