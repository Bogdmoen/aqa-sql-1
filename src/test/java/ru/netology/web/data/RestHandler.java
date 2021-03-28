package ru.netology.web.data;

import com.codeborne.selenide.commands.ToString;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.sql.SQLException;
import java.util.List;

import static ru.netology.web.data.DataHelper.*;

public class RestHandler {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:9999")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendAuthInfo() {
        JsonObject authInfo = new JsonObject();
        authInfo.addProperty("login", returnLoginForTest());
        authInfo.addProperty("password", returnPasswordForTest());
        RestAssured.given()
                .spec(requestSpec)
                .body(authInfo.toString())
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }

    public static String getAuthToken() throws SQLException {
        JsonObject codeInfo = new JsonObject();
        sendAuthInfo();
        String userId = DataHelper.getUserId(returnLoginForTest());
        codeInfo.addProperty("login", returnLoginForTest());
        codeInfo.addProperty("code", DataHelper.getCode(userId));
        return RestAssured.given()
                .spec(requestSpec)
                .body(codeInfo.toString())
                .when()
                .post("/api/auth/verification")
                .then()
                .statusCode(200)
                .extract()
                .path("token")
                .toString();
    }

    public static List<CardInfo> getCardInfo(String token) {
        JsonPath jsonPath = RestAssured.given()
                .spec(requestSpec)
                .auth()
                .oauth2(token)
                .when()
                .get("api/cards")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();
        return jsonPath.getList("", CardInfo.class);
    }


    public static String transferTo(String cardFrom, String cardTo, int amount, String token) {
        int code;
        ResponseBody body;
        JsonObject transferSum = new JsonObject();
        transferSum.addProperty("from", cardFrom);
        transferSum.addProperty("to", cardTo);
        transferSum.addProperty("amount", amount);
        Response response = RestAssured.given()
                .spec(requestSpec)
                .auth()
                .oauth2(token)
                .body(transferSum)
                .when()
                .post("/api/transfer");

        code = response.statusCode();
        body = response.body();
        return code + " " + body.toString();
    }

}
