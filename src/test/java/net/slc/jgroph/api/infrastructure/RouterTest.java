package net.slc.jgroph.api.infrastructure;

import net.slc.jgroph.api.adapters.BookmarksController;
import net.slc.jgroph.infrastructure.container.Container;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("initialization")
public class RouterTest
{
    @Rule public final ExpectedException exception = ExpectedException.none();
    @Mock private HttpServletRequest servletRequest;
    @Mock private HttpServletResponse servletResponse;
    @Mock private Request request;
    @Mock private Response response;
    @Mock private Container container;
    @Mock private Routes routes;
    @Mock private Route route;
    @Mock private BookmarksController bookmarksController;
    @Mock private ActionResolver resolver;
    private Router router;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
            throws RouteNotFoundException, NoSuchMethodException
    {
        when(container.make(ActionResolver.class)).thenReturn(resolver);
        when(container.make(Request.class, servletRequest)).thenReturn(request);
        when(container.make(Response.class, servletResponse)).thenReturn(response);
        when(routes.get(request)).thenReturn(route);
        when(route.getController()).thenReturn((Class)BookmarksController.class);
        when(container.make(BookmarksController.class)).thenReturn(bookmarksController);
        when(route.getAction()).thenReturn("index");
        when(resolver.resolve(bookmarksController.getClass(), "index", Request.class, Response.class))
                .thenReturn(BookmarksController.class.getMethod("index", Request.class, Response.class));

        router = new Router(container, routes);
    }

    @Test
    public void supportsDefaultConstructor()
    {
        final Router router = new Router();
        Assert.assertNotNull(router);
    }

    @Test
    public void convertsRouteNotFoundExceptionToServletException()
            throws RouteNotFoundException, ServletException, IOException
    {
        when(routes.get(request)).thenThrow(RouteNotFoundException.class);
        exception.expect(ServletException.class);
        exception.expectMessage("Only /bookmarks/ is currently supported.");

        router.doGet(servletRequest, servletResponse);
    }

    @Test
    public void routesToIndexBookmarks()
           throws ServletException, IOException
    {
        router.doGet(servletRequest, servletResponse);
        verify(bookmarksController).index(request, response);
    }

    @Test
    public void handlesResponseExceptions()
            throws ServletException, IOException
    {
        final String message = "Error message";
        doThrow(new ResponseException(message)).when(bookmarksController).index(request, response);

        exception.expect(IOException.class);
        exception.expectMessage(message);

        router.doGet(servletRequest, servletResponse);
    }

    @Test
    public void handleActionMismatchExceptions()
            throws NoSuchMethodException, ServletException, IOException
    {
        when(resolver.resolve(bookmarksController.getClass(), "index", Request.class, Response.class))
                .thenThrow(new NoSuchMethodException());
        exception.expect(ServletException.class);
        exception.expectMessage("Invalid action for controller.");

        router.doGet(servletRequest, servletResponse);
    }
}