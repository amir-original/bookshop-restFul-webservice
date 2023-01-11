import com.ws.bookshoprestclient.client.AuthorServiceImpl;
import com.ws.bookshoprestclient.domain.Author;
import com.ws.bookshoprestclient.domain.Book;
import com.ws.bookshoprestclient.domain.BookBuilder;
import com.ws.bookshoprestclient.domain.BookCategory;
import com.ws.bookshoprestclient.domain.exceptions.IsbnNotFoundException;
import com.ws.bookshoprestclient.helper.HttpRequestHandler;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ClientShould {
    private static final String BASE_TARGET = "http://localhost:8080/bookshop-rest-server-1.0-SNAPSHOT/";
    private static final String END_POINT = BASE_TARGET + "api/books";

    private List<Book> cachedBooks = new LinkedList<>();
    private HttpRequestHandler client;

    @BeforeEach
    void setUp() {
        client = new HttpRequestHandler();
    }

    @Test
    void send_get_request_to_server_and_get_all_books() {
        HttpResponse<String> response = client.target(END_POINT).mediaType(MediaType.APPLICATION_JSON).GET().build();
        List<Author> authors = client.getResponse(response, new GenericType<List<Book>>() {}.getType());
        assertNotNull(authors);
    }

    @Test
    void send_get_request_by_id_to_get_book() {
        HttpResponse<String> response = client.target(END_POINT)
                .path("03393748")
                .mediaType(MediaType.APPLICATION_JSON)
                .GET().build();

        Book book = client.getResponse(response, Book.class);
        assertNotNull(book);
        assertEquals("03393748",book.getId());

    }

    @Test
    void send_delete_request_to_delete_book() {
        String id = "55554444";
        Book entity = new BookBuilder().setTitle("name1").setId(id)
                .setDescription("description")
                .setPrice(45.6F)
                .setImagePath("imagePath")
                .setAuthors(List.of(new Author("uncle","bobo")))
                .setCategory(new BookCategory(0,"Software","descrption"))
                .setLink("link")
                .createBook();

        HttpResponse<String> response = client.target(END_POINT)
                .mediaType(MediaType.APPLICATION_JSON).POST(entity).build();
        assertEquals(Response.Status.CREATED.getStatusCode(),response.statusCode());

        assertDoesNotThrow(()-> client.target(END_POINT).path(id)
                .mediaType(MediaType.APPLICATION_JSON)
                .DELETE().build());
    }
}
