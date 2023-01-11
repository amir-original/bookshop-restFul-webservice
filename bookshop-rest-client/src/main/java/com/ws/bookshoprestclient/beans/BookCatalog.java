package com.ws.bookshoprestclient.beans;

import com.ws.bookshoprestclient.client.BookService;
import com.ws.bookshoprestclient.domain.Book;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
@Named
@RequestScoped
public class BookCatalog {
    @Inject
    private BookService bookService;

    private List<Book> books;

    @PostConstruct
    public void initialize(){
        books = bookService.getAll();
    }

    public List<Book> getBooks()   {
        return books;
    }

}
