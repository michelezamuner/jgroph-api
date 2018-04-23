package net.slc.jgroph.api.infrastructure.http_server;

import javax.servlet.http.HttpServletRequest;

public class Request
{
    private final HttpServletRequest request;

    public Request(final HttpServletRequest request)
    {
        this.request = request;
    }

    String getPath()
    {
        return request.getRequestURI();
    }
}