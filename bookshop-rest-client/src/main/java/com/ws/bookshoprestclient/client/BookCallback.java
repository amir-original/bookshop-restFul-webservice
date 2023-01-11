package com.ws.bookshoprestclient.client;

import com.ws.bookshoprestclient.domain.Book;
import jakarta.ws.rs.client.InvocationCallback;

import java.util.ArrayList;

public class BookCallback implements InvocationCallback<ArrayList<Book>> {

    @Override
    public void completed(ArrayList<Book> books) {

    }

    @Override
    public void failed(Throwable throwable) {

    }
}