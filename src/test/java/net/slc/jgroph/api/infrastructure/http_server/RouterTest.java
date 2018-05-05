package net.slc.jgroph.api.infrastructure.http_server;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class RouterTest
{
    @Rule public final ExpectedException exception = ExpectedException.none();
    private final Faker faker = new Faker();
    private RequestMethod method;
    private String path;
    @Mock private Factory factory;
    @Mock private HttpServletRequest servletRequest;
    @Mock private HttpServletResponse servletResponse;
    @Mock private Request request;
    @Mock private Response response;
    @Mock private Routes routes;
    private Router router;

    @Before
    public void setUp()
    {
        method = RequestMethod.values()[faker.number().numberBetween(0, RequestMethod.values().length)];
        path = faker.lorem().word();
        when(request.getMethod()).thenReturn(method);
        when(request.getPath()).thenReturn(path);
        when(factory.createRequest(servletRequest)).thenReturn(request);
        when(factory.createResponse(servletResponse)).thenReturn(response);
        router = new Router(routes, factory);
    }

    @Test
    public void executesCorrectAction()
            throws HttpException, ServletException, IOException
    {
        final Action action = mock(Action.class);
        when(routes.getAction(method, path)).thenReturn(Optional.of(action));

        router.service(servletRequest, servletResponse);

        verify(action).exec(request, response);
    }

    @Test
    public void throwServletExceptionIfNoHandlerFound()
            throws HttpException, ServletException, IOException
    {
        final String message = faker.lorem().sentence();
        exception.expect(ServletException.class);
        exception.expectMessage(message);

        final Action action = mock(Action.class);
        doThrow(new HttpException(message)).when(action).exec(request, response);
        when(routes.getAction(method, path)).thenReturn(Optional.of(action));

        router.service(servletRequest, servletResponse);
    }

    @Test
    public void routesToNotFoundIfNoActionFound()
            throws ServletException, IOException
    {
        when(routes.getAction(method, path)).thenReturn(Optional.empty());

        final Handler handler = mock(Handler.class);
        when(routes.getHandler(NotFoundException.class)).thenReturn(Optional.of(handler));

        router.service(servletRequest, servletResponse);

        final String message = "No route found for " + path;
        verify(handler).handle(eq(request), eq(response), argThat(e -> e.getMessage().equals(message)));
    }

    @Test
    public void routesToHandlerOnException()
            throws HttpException, ServletException, IOException
    {
        final HttpException expected = mock(HttpException.class);
        final Action action = mock(Action.class);
        final Handler handler = mock(Handler.class);

        doThrow(expected).when(action).exec(request, response);
        when(routes.getAction(method, path)).thenReturn(Optional.of(action));
        when(routes.getHandler(expected.getClass())).thenReturn(Optional.of(handler));

        router.service(servletRequest, servletResponse);

        verify(handler).handle(request, response, expected);
    }
}