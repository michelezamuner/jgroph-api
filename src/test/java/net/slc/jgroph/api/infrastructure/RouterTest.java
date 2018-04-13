package net.slc.jgroph.api.infrastructure;

import net.slc.jgroph.api.adapters.BookmarksController;
import net.slc.jgroph.infrastructure.container.Container;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("initialization")
public class RouterTest
{
    @Rule public final ExpectedException expectedException = ExpectedException.none();
    @Mock private HttpServletRequest servletRequest;
    @Mock private HttpServletResponse servletResponse;
    @Mock private Request request;
    @Mock private Response response;
    @Mock private Container container;
    @Mock private Routes routes;
    @Mock private Route route;
    @Mock private BookmarksController bookmarksController;
    @InjectMocks private Router router;

    @Before
    public void setUp()
            throws RouteNotFoundException
    {
        when(container.make(Request.class, servletRequest)).thenReturn(request);
        when(container.make(Response.class, servletResponse)).thenReturn(response);
        when(container.make(BookmarksController.class)).thenReturn(bookmarksController);
        when(routes.get(request)).thenReturn(route);
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
        expectedException.expect(ServletException.class);

        router.doGet(servletRequest, servletResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void routesToIndexBookmarks()
           throws ServletException, IOException
    {
        when(route.getController()).thenReturn((Class)BookmarksController.class);
        when(route.getAction()).thenReturn("index");

        router.doGet(servletRequest, servletResponse);
        verify(bookmarksController).index(request, response);
    }
}