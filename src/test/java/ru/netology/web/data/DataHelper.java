package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    private Faker faker = new Faker(new Locale("ru"));
    private static final String loginForTest = "vasya";
    private static final String passwordForTest = "qwerty123";

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String id;
        private String login;
        private String password;
        private String status;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class  VerificationCode {
        private String code;
    }

    public static List<UserInfo> getUsers(String login) {
        return DataBase.getUsers();
    }

    public static UserInfo getUserByLogin(String login) {
        return DataBase.getUser(login);
    }

    public static String getUserId(String login) {
        return getUserByLogin(login).getId();
    }

    public static String getCode(String userId) {
        return DataBase.getAuthCode(userId).getCode();
    }

    public static void cleanAll() {
        DataBase.cleanAll();
    }

    public static String returnLoginForTest() {
        return loginForTest;
    }

    public static String returnPasswordForTest() {
        return passwordForTest;
    }

    public static List<CardInfo> returnCardsForUser() throws SQLException {
        return DataBase.returnCardsForUser(getUserId(returnLoginForTest()));
    }

    public static int returnCardBalance(String cardNumber) throws SQLException {
        return DataBase.returnCardInfo(cardNumber).getBalance();
    }

    public static void cleanCode() throws SQLException {
        DataBase.cleanCode();
    }

}
