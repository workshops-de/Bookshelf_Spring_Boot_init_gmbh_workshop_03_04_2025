package de.workshops.bookshelf.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

interface BookJpaRepository extends ListCrudRepository<Book, Long> {
    List<Book> findByAuthorContaining(String author);

    Book findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1% OR b.author LIKE %?1%")
    List<Book> search (BookSearchRequest request);
}
