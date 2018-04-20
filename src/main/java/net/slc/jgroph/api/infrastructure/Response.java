package net.slc.jgroph.api.infrastructure;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    public PrintWriter getWriter()
            throws ResponseException
    {
        try {
            return response.getWriter();
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error accessing the response writer." : e.getMessage();
            throw new ResponseException(message, e);
        }
    }
}