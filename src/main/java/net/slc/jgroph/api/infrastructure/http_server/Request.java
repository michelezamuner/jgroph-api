package net.slc.jgroph.api.infrastructure.http_server;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.servlet.http.HttpServletRequest;

public class Request
{
    private final HttpServletRequest request;

    public Request(final HttpServletRequest request)
    {
        this.request = request;
    }

    RequestMethod getMethod()
    {
        return RequestMethod.valueOf(request.getMethod());
    }

    String getPath()
    {
        return request.getRequestURI();
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == null || !(o instanceof Request)) {
            return false;
        }

        return request.equals(((Request) o).request);
    }

    @Override
    public int hashCode() {
        // Hash base: 2. Hash mixer: 37. Check https://stackoverflow.com/questions/113511#answer-113600
        return 74 + request.hashCode();
    }
}