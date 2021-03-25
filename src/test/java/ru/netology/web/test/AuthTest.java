package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataBase;
import ru.netology.web.data.DataHelper;

import java.sql.SQLException;

public class AuthTest {


    @Test
    public void shouldGetUserInfo() throws SQLException {
        DataBase.getUsers();
    }

    @Test
    public void shouldReturnUser() throws SQLException {
     DataHelper.UserInfo result =  DataHelper.getUserByLogin("vasya");
     System.out.println(result);
    }

    @Test
    public void shouldGetCode() throws SQLException {
        System.out.println("=" + DataBase.getAuthCode("8f6b8826-fdd4-4249-ab4a-9419ab4873e4"));
    }

    @Test
    public void shouldDeleteFromTable() throws SQLException {
        DataBase.cleanAll();
    }
}
