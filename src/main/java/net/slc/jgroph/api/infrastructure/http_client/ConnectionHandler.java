package net.slc.jgroph.api.infrastructure.http_client;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

class ConnectionHandler
{
    URLConnection open(final String url)
            throws IOException
    {
        return getUrl(url).openConnection();
    }

    private URL getUrl(final String url)
        throws IOException
    {
        try {
            return new URL(url);
        } catch (IOException e) {
            final String message = e.getMessage() == null ? "Unexpected empty error message" : e.getMessage();
            throw new IOException("Invalid URL " + url + ": " + message, e);
        }
    }
}