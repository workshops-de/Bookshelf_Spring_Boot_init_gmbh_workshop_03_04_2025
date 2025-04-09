package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class BookJdbcClientRepository {
    private final JdbcClient jdbcClient;

    BookJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Book> findAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcClient.sql(sql)
                .query(new BeanPropertyRowMapper<>(Book.class))
                .list();
    }

    Book saveBook(Book book) {
        String sql = "INSERT INTO book (title, description, author, isbn) VALUES (:title, :description, :author, :isbn)";

        jdbcClient.sql(sql)
                .param("title", book.getTitle())
                .param("description", book.getDescription())
                .param("author", book.getAuthor())
                .param("isbn", book.getIsbn())
                .update();
        return book;
    }
}
