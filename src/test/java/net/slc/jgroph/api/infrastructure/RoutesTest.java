package net.slc.jgroph.api.infrastructure;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertSame;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class RoutesTest
{
    @Rule public final ExpectedException exception = ExpectedException.none();
    @Mock private Request request;
    private Routes routes;

    @Before
    public void setUp()
    {
        when(request.getPath()).thenReturn("/bookmarks/");

        routes = new Routes(ControllerDouble.class);
    }

    @Test
    public void findsIndexBookmarksRoute()
            throws RouteNotFoundException
    {
        final Route route = routes.get(request);
        assertSame(ControllerDouble.class, route.getController());
        assertSame("index", route.getAction());
    }

    @Test
    public void failsIfRouteNotFound()
            throws RouteNotFoundException
    {
        final String path = "invalid path";
        when(request.getPath()).thenReturn(path);
        exception.expect(RouteNotFoundException.class);
        exception.expectMessage("No route found for " + path + ".");

        routes.get(request);
    }
}