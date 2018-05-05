package net.slc.jgroph.api.infrastructure.http_server;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Response
{
    public static final int SC_OK = HttpServletResponse.SC_OK;

    private final HttpServletResponse response;

    public Response(final HttpServletResponse response)
    {
        this.response = response;
    }

    public void setJsonContentType()
    {
        response.setHeader("Content-Type", "application/json");
    }

    public void write(final String content)
            throws ResponseException
    {
        try {
            response.getWriter().print(content);
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error writing the response." : e.getMessage();
            throw new ResponseException(message, e);
        }
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == null || !(o instanceof Response)) {
            return false;
        }

        return response.equals(((Response) o).response);
    }

    @Override
    public int hashCode() {
        // Hash base: 2. Hash mixer: 37. Check https://stackoverflow.com/questions/113511#answer-113600
        return 74 + response.hashCode();
    }
}