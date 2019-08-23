package com.github.gatekeeper4j;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GateKeeperTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/github/?123123123123")
          .then()
             .statusCode(422);
    }

}