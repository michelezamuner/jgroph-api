package net.slc.jgroph.api.adapters;

import com.github.javafaker.Faker;
import net.slc.jgroph.api.infrastructure.http_server.Router;
import net.slc.jgroph.api.infrastructure.http_server.Server;
import net.slc.jgroph.infrastructure.container.Container;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.InetSocketAddress;

import static org.mockito.Mockito.*;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class LauncherTest
{
    private final Faker faker = new Faker();
    @Mock private Container container;
    @Mock private Provider provider;
    @Mock private Server server;
    @Mock private ServletContextHandler handler;
    @Mock private Router router;
    @Mock private ServletHolder holder;
    private String host;
    private int port;
    private Launcher launcher;

    @Before
    public void setUp()
    {
        host = faker.internet().ipV4Address();
        port = faker.number().numberBetween(1025, 65535);
        final InetSocketAddress address = new InetSocketAddress(host, port);

        when(container.make(Server.class, address)).thenReturn(server);
        when(container.make(ServletContextHandler.class, ServletContextHandler.SESSIONS)).thenReturn(handler);
        when(container.make(Router.class)).thenReturn(router);
        when(container.make(ServletHolder.class, router)).thenReturn(holder);

        launcher = new Launcher(container, provider);
    }

    @Test
    public void routerIsRegisteredWithServer()
            throws Exception
    {
        launcher.launch(host, port);

        verify(handler).setContextPath("/");
        verify(handler).addServlet(holder, "/*");
        verify(server).setHandler(handler);
        verify(server).start();
        verify(server).join();
    }

    @Test
    public void providerIsRegisteredWhenTheApplicationIsLaunched()
            throws Exception
    {
        launcher.launch(host, port);
        verify(provider).register(any(Container.class));
    }
}