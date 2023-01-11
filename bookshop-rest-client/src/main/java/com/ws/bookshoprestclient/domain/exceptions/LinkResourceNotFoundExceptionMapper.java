package com.ws.bookshoprestclient.domain.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class LinkResourceNotFoundExceptionMapper implements ExceptionMapper<LinkResourceNotFoundException> {

    @Override
    public Response toResponse(LinkResourceNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
