package com.aayush.microservice.product;

import com.aayush.microservice.product.dto.ProductRequest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import java.math.BigDecimal;

@Import(TestcontainersConfiguration.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // run test on random port instead of 8080
class ProductServiceApplicationTests {

    //init mongodb container
    @ServiceConnection // automatically inject uri
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI =  "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }

    @Test
    void shouldCreateProduct() {
        // this test should create the product and we verify it
        ProductRequest productRequset = getProductRequest();
        RestAssured.given()
                .contentType("application/json")
                .body(productRequset)
                .when()
                .post("api/product")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo(productRequset.name()))
                .body("description", Matchers.equalTo(productRequset.description()))
                .body("price", Matchers.equalTo(productRequset.price().floatValue()));
    }

    private ProductRequest getProductRequest() {
        return new ProductRequest("id","test iphone 13", "test iphone 13 is an apple product", BigDecimal.valueOf(1200.00));
    }
}