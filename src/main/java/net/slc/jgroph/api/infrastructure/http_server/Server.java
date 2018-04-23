package net.slc.jgroph.api.infrastructure.http_server;

import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * This wrapper is needed to be able to unit test the Launcher, since some methods of the Jetty Server are final, and
 * thus cannot be mocked.
 */
public class Server
{
    private final org.eclipse.jetty.server.Server server;

    public Server(final org.eclipse.jetty.server.Server server)
    {
        this.server = server;
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