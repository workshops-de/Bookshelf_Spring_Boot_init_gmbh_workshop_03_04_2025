package de.workshops.bookshelf.book;

import de.workshops.bookshelf.MailConfiguration;
import de.workshops.bookshelf.MethodSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
@Import({MailConfiguration.class, MethodSecurityConfiguration.class})
//@SpringBootTest
//@AutoConfigureMockMvc
@WithMockUser
class BookRestControllerMockMvcMockitoTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookService bookService;

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @Captor
    ArgumentCaptor<String> isbnCaptor;

    @Test
    void getAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(new Book(), new Book()));

        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBookByIsbn_nonExisting() throws Exception {
        when(bookService.getBookByIsbn(anyString())).thenThrow(new BookException("Book not found"));

        mockMvc.perform(get("/book/978-3836211162"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBookByIsbn_existing() throws Exception {
        when(bookService.getBookByIsbn(isbnCaptor.capture())).thenReturn(new Book());

        mockMvc.perform(get("/book/978-3836211162"))
                .andExpect(status().isOk());

        assertThat(isbnCaptor.getValue()).isEqualTo("978-3836211162");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook() throws Exception {
        var isbn = "111-1111111111";
        var title = "My first book";
        var author = "Birgit Kratz";
        var description = "A must read!!";

        when(bookService.createBook(bookCaptor.capture())).thenReturn(new Book());

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .with(csrf()))
                .andExpect(status().isOk());

//        verify(bookService).createBook(any(Book.class));
//        var value = bookCaptor.getValue();
//        assertThat(value.getIsbn()).isNotNull();
    }


    @Test
    void createBook_wrongRole() throws Exception {
        var isbn = "111-1111111111";
        var title = "My first book";
        var author = "Birgit Kratz";
        var description = "A must read!!";

        when(bookService.createBook(bookCaptor.capture())).thenReturn(new Book());

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}