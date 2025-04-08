package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> getAllBooks() {
       return bookRepository.findAllBooks();
    }

    Book getBookByIsbn(String isbn) {
        return bookRepository.findAllBooks().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new BookException("Sorry, no book with this ISBN"));
    }

    List<Book> getBookByAuthor(String author) {
        return bookRepository.findAllBooks().stream()
                .filter(book -> book.getAuthor().contains(author))
                .toList();
    }

    List<Book> searchBooks(BookSearchRequest searchRequest) {
        return bookRepository.findAllBooks().stream()
                .filter(book -> book.getAuthor().contains(searchRequest.author())
                                || book.getTitle().contains(searchRequest.title()))
                .toList();
    }

    Book createBook(Book book) {
        return bookRepository.saveBook(book);
    }
}
