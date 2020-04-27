package net.slc.jgroph.api.infrastructure.http_server;

import org.eclipse.jetty.servlet.ServletContextHandler;

import java.net.InetSocketAddress;

/**
 * This wrapper is needed to be able to unit test the Launcher, since some methods of the Jetty Server are final, and
 * thus cannot be mocked.
 */
public class Server
{
    private final org.eclipse.jetty.server.Server server;

    public Server(final InetSocketAddress address)
    {
        server = new org.eclipse.jetty.server.Server(address);
    }

    public void setHandler(final ServletContextHandler handler)
    {
        server.setHandler(handler);
    }

    public void start()
            throws Exception
    {
        server.start();
    }

    public void join()
            throws InterruptedException
    {
        server.join();
    }
}