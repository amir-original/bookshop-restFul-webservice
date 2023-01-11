package com.ws.bookshoprestclient.client;

import com.ws.bookshoprestclient.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAuthors();

    Author getAuthor(String id);

    Author saveAuthor(Author author);

    void deleteAuthor(String id);

}
