package net.slc.jgroph.api.infrastructure.http_client;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

class ConnectionHandler
{
    URLConnection open(final String url)
            throws IOException
    {
        return new URL(url).openConnection();
    }
}