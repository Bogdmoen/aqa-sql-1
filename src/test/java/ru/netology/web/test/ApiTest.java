package ru.netology.web.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.netology.web.data.DataBase;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.RestHandler;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ApiTest {
    static String token;
    static String cardOne;
    static String cardTwo;

    @BeforeAll
    public static void setUp() throws SQLException {
        DataHelper.cleanCode();
        token = RestHandler.getAuthToken();
        cardOne = DataHelper.returnCardsForUser().get(0).getNumber();
        cardTwo = DataHelper.returnCardsForUser().get(1).getNumber();
    }


    @Test
    public void shouldGetToken() throws SQLException {
        System.out.println(RestHandler.getAuthToken());
    }

    @Test
    public void shouldGetCardInfo() throws SQLException {
        System.out.println("result is = " + RestHandler.getCardInfo(token));
    }

    @Test
    public void shouldTransferSum() throws SQLException {
        int amount = 0;
        int initBalance = DataHelper.returnCardBalance(cardOne);
        String result = RestHandler.transferTo(cardOne, cardTwo, amount, token);
        int expected = initBalance - amount;
        int actual = DataHelper.returnCardBalance(cardOne);
        assertEquals(expected, actual);
        System.out.println(DataHelper.returnCardsForUser());
        System.out.println(result);
    }
}

