package com.ws.bookshoprestclient.beans;

import com.ws.bookshoprestclient.client.BookService;
import com.ws.bookshoprestclient.domain.Book;
import com.ws.bookshoprestclient.domain.exceptions.LinkResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.ListDataModel;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class BookBean {

    @Inject
    private BookService bookService;

    private List<Book> books;

    @PostConstruct
    public void init(){
        books = bookService.getAll();
    }

    public List<Book> getBooks() {
        return books;
    }

    public String deleteBook(String id){
        try {
            bookService.deleteBook(id);
        } catch (LinkResourceNotFoundException e) {
            e.printStackTrace();
        }
        return "success?faces-redirect=true&includeViewParams=true";
    }

}
