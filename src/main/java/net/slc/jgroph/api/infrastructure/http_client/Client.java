package net.slc.jgroph.api.infrastructure.http_client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class Client
{
    public Response sendGet(final Url url)
            throws ClientException
    {
        final HttpURLConnection connection = getConnection(url);
        final BufferedReader reader = getReader(connection);
        final StringBuilder responseBody = new StringBuilder();

        try {
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                responseBody.append(responseLine);
            }
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error reading response line." : e.getMessage();
            throw new ClientException(message, e);
        } finally {
            closeReader(reader);
        }

        try {
            return new Response(connection.getResponseCode(), connection.getContentType(), responseBody.toString());
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error getting response code." : e.getMessage();
            throw new ClientException(message, e);
        }
    }

    private HttpURLConnection getConnection(final Url url)
            throws ClientException
    {
        try {
            final HttpURLConnection connection = url.openConnection();
            connection.setRequestMethod("GET");
            return connection;
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error opening connection." : e.getMessage();
            throw new ClientException(message, e);
        }
    }

    private BufferedReader getReader(final HttpURLConnection connection)
            throws ClientException
    {
        try {
            return new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error opening connection stream." : e.getMessage();
            throw new ClientException(message, e);
        }
    }

    private void closeReader(final Closeable reader)
            throws ClientException
    {
        try {
            reader.close();
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Error closing response reader." : e.getMessage();
            throw new ClientException(message, e);
        }
    }
}