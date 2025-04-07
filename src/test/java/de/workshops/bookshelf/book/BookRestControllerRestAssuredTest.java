package de.workshops.bookshelf.book;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookRestControllerRestAssuredTest {

    @Autowired
    BookRestController bookRestController;

    @Test
    void getAllBooks() {
        RestAssuredMockMvc.standaloneSetup(bookRestController);
        RestAssuredMockMvc.when().get("/book")
                .then()
                .statusCode(200);
    }
}