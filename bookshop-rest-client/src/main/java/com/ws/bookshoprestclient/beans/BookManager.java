package com.ws.bookshoprestclient.beans;

import com.ws.bookshoprestclient.client.AuthorService;
import com.ws.bookshoprestclient.client.BookService;
import com.ws.bookshoprestclient.domain.Book;
import com.ws.bookshoprestclient.domain.exceptions.LinkResourceNotFoundException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class BookManager implements Serializable {

    private static final long serialVersionUID = 1;

    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    private String id;

    private Book book;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void onLoad() {
        book = bookService.getById(id);
    }

    public String submit(Book book) {
        book = bookService.saveBook(book);
        return "book-details?id=" + book.getId();
    }

    public String delete(String id) throws LinkResourceNotFoundException {
        System.out.println("delete id: " + id);
        bookService.deleteBook(id);
        return "admin.xhtml?delete=success&faces-redirect=true";
    }

}
