package net.slc.jgroph.api.infrastructure.http_client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This wrapper is needed to be able to unit test the Client, since the URL class is final, and thus cannot be mocked.
 */
public class Url
{
    private final URL url;

    public Url(final String url)
            throws MalformedURLException
    {
        this.url = new URL(url);
    }

    public HttpURLConnection openConnection()
            throws IOException
    {
        return (HttpURLConnection)url.openConnection();
    }
}