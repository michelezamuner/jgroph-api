package net.slc.jgroph.api.infrastructure.http_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Client
{
    private final ConnectionHandler connectionHandler;

    public Client()
    {
        this(new ConnectionHandler());
    }

    Client(final ConnectionHandler connectionHandler)
    {
        this.connectionHandler = connectionHandler;
    }

    public Response sendGet(final String url)
            throws ClientException
    {
        final HttpURLConnection connection = (HttpURLConnection)getConnection(url);

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message." : e.getMessage();
            throw new ClientException(message, e);
        }

        try {
            return new Response(connection.getResponseCode(), connection.getContentType(), getResponseBody(connection));
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message." : e.getMessage();
            throw new ClientException(message, e);
        }
    }

    private URLConnection getConnection(final String url)
            throws ClientException
    {
        try {
            return connectionHandler.open(url);
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message." : e.getMessage();
            throw new ClientException(message, e);
        }
    }

    private String getResponseBody(final URLConnection connection)
            throws ClientException
    {
        final BufferedReader reader = getResponseReader(connection);

        final StringBuilder body = new StringBuilder();
        String bodyLine;
        try {
            while ((bodyLine = reader.readLine()) != null) {
                body.append(bodyLine);
            }
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message." : e.getMessage();
            throw new ClientException(message, e);
        } finally {
            closeResponseReader(reader);
        }

        return body.toString();
    }

    private BufferedReader getResponseReader(final URLConnection connection)
            throws ClientException
    {
        try {
            return new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8));
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message." : e.getMessage();
            throw new ClientException(message, e);
        }
    }

    private void closeResponseReader(final Reader reader)
            throws ClientException
    {
        try {
            reader.close();
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message." : e.getMessage();
            throw new ClientException(message, e);
        }
    }
}