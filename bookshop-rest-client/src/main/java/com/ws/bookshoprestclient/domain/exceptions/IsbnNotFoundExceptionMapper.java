package com.ws.bookshoprestclient.domain.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IsbnNotFoundExceptionMapper implements ExceptionMapper<IsbnNotFoundException> {

    @Override
    public Response toResponse(IsbnNotFoundException exception) {
        return Response.status(Response.Status.FOUND).build();
    }
}
