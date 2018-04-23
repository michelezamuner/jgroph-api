package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.infrastructure.http_server.Router;
import net.slc.jgroph.api.infrastructure.http_server.Server;
import net.slc.jgroph.infrastructure.container.Container;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.InetSocketAddress;

public class Launcher
{
    private final Container container;
    private final Provider provider;

    public static void main(final String[] args)
            throws Exception
    {
        if (args.length < 2) {
            throw new IllegalArgumentException("Server host and port are required.");
        }

        new Launcher(new Container(), new Provider()).launch(args[0], Integer.parseInt(args[1]));
    }

    Launcher(final Container container, final Provider provider)
    {
        this.container = container;
        this.provider = provider;
    }

    void launch(final String host, final int port)
            throws Exception
    {
        provider.register(container);

        final Server server = container.make(Server.class, new InetSocketAddress(host, port));
        final ServletContextHandler handler =
                container.make(ServletContextHandler.class, ServletContextHandler.SESSIONS);
        final ServletHolder holder = container.make(ServletHolder.class, container.make(Router.class));

        handler.setContextPath("/");
        server.setHandler(handler);
        handler.addServlet(holder, "/*");
        server.start();
        server.join();
    }
}