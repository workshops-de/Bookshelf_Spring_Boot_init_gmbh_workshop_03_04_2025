package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookServiceUsingJpaRepository {
    private final BookJpaRepository bookRepository;

    BookServiceUsingJpaRepository(BookJpaRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> getAllBooks() {
       return bookRepository.findAll();
    }

    Book getBookByIsbn(String isbn) {
        var book = bookRepository.findByIsbn(isbn);

        if (book == null) {
            throw new BookException("Sorry, no book with this ISBN");
        }
        return book;
    }

    List<Book> getBookByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    List<Book> searchBooks(BookSearchRequest searchRequest) {
        return bookRepository.search(searchRequest);
    }

    Book createBook(Book book) {
        return bookRepository.save(book);
    }
}
