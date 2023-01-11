package com.ws.bookshoprestclient.client;

import com.ws.bookshoprestclient.domain.Book;
import com.ws.bookshoprestclient.domain.LinkResource;
import com.ws.bookshoprestclient.domain.exceptions.LinkResourceNotFoundException;
import com.ws.bookshoprestclient.helper.HttpRequestHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    private static final String BASE_TARGET = "http://localhost:8080/bookshop-rest-server-1.0-SNAPSHOT/";
    private static final String END_POINT = BASE_TARGET + "api/books";

    private List<Book> cachedBooks = new LinkedList<>();
    private HttpRequestHandler client;

    @PostConstruct
    public void init() {
        client = new HttpRequestHandler();
    }

    @Override
    public List<Book> getAll() {
        cachedBooks.clear();
        HttpResponse<String> response = client.target(END_POINT).mediaType(MediaType.APPLICATION_JSON).GET().build();
        cachedBooks = client.getResponse(response, new GenericType<List<Book>>() {
        }.getType());
        return Collections.unmodifiableList(cachedBooks);
    }

    @Override
    public Book getById(String id) {
        HttpResponse<String> response = client.target(END_POINT)
                .path(id)
                .mediaType(MediaType.APPLICATION_JSON)
                .GET().build();

        return client.getResponse(response, Book.class);
    }

    @Override
    public Book saveBook(Book book) {
        HttpResponse<String> response = client.target(END_POINT)
                .mediaType(MediaType.APPLICATION_JSON)
                .POST(book).build();

        return client.getResponse(response, Book.class);
    }

    @Override
    public void deleteBook(String id) throws LinkResourceNotFoundException {
        Optional<Book> first = cachedBooks.stream().filter(book -> book.getId().equals(id)).findFirst();
        if (first.isPresent()){
            Optional<LinkResource> delete = first.get().getLinks().stream().filter(ls -> ls.getRel().equals("delete")).findFirst();
            LinkResource deleteLink = delete.orElseThrow(LinkResourceNotFoundException::new);
            client.target(deleteLink.getUri().toString())
                    .mediaType(MediaType.APPLICATION_JSON)
                    .DELETE().build();
        }

    }

    @PreDestroy
    public void cleanUp(){
        cachedBooks = null;
    }
}
