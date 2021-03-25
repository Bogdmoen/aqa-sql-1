package ru.netology.web.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataHelper {
    private DataHelper() {
    }

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

    public static List<UserInfo> getUsers(String login) throws SQLException {
        return DataBase.getUsers();
    }

    public static UserInfo getUserByLogin(String login) throws SQLException {
        return DataBase.getUser(login);
    }

    public static String getUserId(String login) throws SQLException {
        return getUserByLogin(login).getId();
    }

    public static String getCode(String userId) throws SQLException {
        return DataBase.getAuthCode(userId).getCode();
    }

    public static void cleanAll() throws SQLException {
        DataBase.cleanAll();
    }


}
