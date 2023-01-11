package com.ws.bookshoprestclient.client;

import com.ws.bookshoprestclient.domain.Book;
import com.ws.bookshoprestclient.domain.exceptions.LinkResourceNotFoundException;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getById(String id);

    Book saveBook(Book book);

    void deleteBook(String id) throws LinkResourceNotFoundException;
}
