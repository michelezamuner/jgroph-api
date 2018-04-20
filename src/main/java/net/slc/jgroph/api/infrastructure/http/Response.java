package net.slc.jgroph.api.infrastructure.http;

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
}