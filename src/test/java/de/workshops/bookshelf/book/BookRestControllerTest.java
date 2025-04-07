package de.workshops.bookshelf.book;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BookRestControllerTest {

    @Autowired
    BookRestController bookRestController;

    @Test
    void getAllBooks() {
        var allBooks = bookRestController.getAllBooks();
        assertThat(allBooks).hasSize(3);
    }

    @Test
    void getByIsbn_Existing() {
        var bookByIsbn = bookRestController.getBookByIsbn("978-3836211161");
        assertThat(bookByIsbn).isNotNull();
        assertThat(bookByIsbn.getTitle()).isEqualTo("Coding for Fun");
    }

    @Test
    void getByIsbn_notExisting() {
        assertThatThrownBy(() ->bookRestController.getBookByIsbn("978-3836211162"))
                .isInstanceOf(BookException.class);
    }

    @Test
    void getByIsbn_invalidIsbn() {
        assertThatThrownBy(() ->bookRestController.getBookByIsbn("978-"))
                .isInstanceOf(ConstraintViolationException.class);
    }
}