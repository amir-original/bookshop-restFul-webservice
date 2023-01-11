package com.ws.bookshoprestclient.client;

import com.ws.bookshoprestclient.domain.Author;
import com.ws.bookshoprestclient.helper.HttpRequestHandler;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
@ApplicationScoped
public class AuthorServiceImpl implements AuthorService {
    private static final String BASE_TARGET = "http://localhost:8080/bookshop-rest-server-1.0-SNAPSHOT/";
    private static final String END_POINT = BASE_TARGET + "api/authors";
    private HttpRequestHandler client;
    private List<Author> authorsCached = new LinkedList<>();

    @PostConstruct
    public void init(){
        client = new HttpRequestHandler();
    }
    @Override
    public List<Author> getAuthors() {
        HttpResponse<String> response = client.target(END_POINT).mediaType(MediaType.APPLICATION_JSON).GET().build();
        authorsCached = client.getResponse(response,new GenericType<List<Author>>(){}.getType());
        return Collections.unmodifiableList(authorsCached);
    }

    @Override
    public Author getAuthor(String id) {
        HttpResponse<String> response = client.target(END_POINT)
                .path(id).mediaType(MediaType.APPLICATION_JSON)
                .GET().build();
        return client.getResponse(response,Author.class);
    }

    @Override
    public Author saveAuthor(Author author) {
        HttpResponse<String> response = client.target(END_POINT)
                .mediaType(MediaType.APPLICATION_JSON)
                .POST(author).build();

        return client.getResponse(response,Author.class);

    }

    @Override
    public void deleteAuthor(String id) {
        client.target(END_POINT).path(id).mediaType(MediaType.APPLICATION_JSON).DELETE().build();
    }
}
