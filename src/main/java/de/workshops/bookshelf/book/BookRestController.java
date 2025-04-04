package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
class BookRestController {

    private final BookService bookService;

    BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    Book getBookByIsbn(@PathVariable(name = "isbn") @Size(min=10, max = 17) String isbnNumber) {
        return bookService.getBookByIsbn(isbnNumber);
    }

    @GetMapping(params = "author")
    ResponseEntity<List<Book>> getBookByAuthor(@RequestParam(name="author") @Size(min=3) String authorName) {
        var list = bookService.getBookByAuthor(authorName);

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/search")
    List<Book> searchBooks(@RequestBody @Valid BookSearchRequest searchRequest) {
        return bookService.searchBooks(searchRequest);
    }

    @GetMapping("/searchwithget")
    List<Book> searchBooksWithGetRequest(@Valid BookSearchRequest searchRequest) {
        return bookService.searchBooks(searchRequest);
    }

    @ExceptionHandler(BookException.class)
    ResponseEntity<String> bookExceptionHandler(BookException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
