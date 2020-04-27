package net.slc.jgroph.api.infrastructure.http_server;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

@SuppressWarnings("initialization")
public class RoutesTest
{
    private Routes routes;

    @Before
    public void setUp()
    {
        routes = new Routes();
    }

    @Test
    public void registerActionWithExactMatch()
    {
        final Action expectedGetAllBookmarks = mock(Action.class);
        final Action expectedGetSingleBookmark = mock(Action.class);
        final Action expectedPostNewBookmark = mock(Action.class);

        routes.addAction(RequestMethod.GET, "/bookmarks/", expectedGetAllBookmarks);
        routes.addAction(RequestMethod.GET, "/bookmarks/1234", expectedGetSingleBookmark);
        routes.addAction(RequestMethod.POST, "/bookmarks/", expectedPostNewBookmark);

        final Optional<Action> actionGetAllBookmarks = routes.getAction(RequestMethod.GET, "/bookmarks/");
        assertSame(expectedGetAllBookmarks, actionGetAllBookmarks.get());

        final Optional<Action> actionGetSingleBookmark = routes.getAction(RequestMethod.GET, "/bookmarks/1234");
        assertSame(expectedGetSingleBookmark, actionGetSingleBookmark.get());

        final Optional<Action> actionPostNewBookmark = routes.getAction(RequestMethod.POST, "/bookmarks/");
        assertSame(expectedPostNewBookmark, actionPostNewBookmark.get());
    }

    @Test
    public void registerActionWithRegexMatch()
    {
        final Action expected = mock(Action.class);

        routes.addAction(RequestMethod.GET, "/bookmarks/?.*", expected);

        final Optional<Action> actionSingleBookmark = routes.getAction(RequestMethod.GET, "/bookmarks/1234");
        assertSame(expected, actionSingleBookmark.get());

        final Optional<Action> actionAllBookmarks = routes.getAction(RequestMethod.GET, "/bookmarks");
        assertSame(expected, actionAllBookmarks.get());
    }

    @Test
    public void registerHandlerWithExactExceptionClass()
    {
        final Handler expectedRuntimeException = mock(Handler.class);
        final Handler expectedIllegalArgumentException = mock(Handler.class);

        routes.addHandler(RuntimeException.class, expectedRuntimeException);
        routes.addHandler(IllegalArgumentException.class, expectedIllegalArgumentException);

        final Optional<Handler> handlerRuntimeException = routes.getHandler(RuntimeException.class);
        assertSame(expectedRuntimeException, handlerRuntimeException.get());

        final Optional<Handler> handlerIllegalArgumentException = routes.getHandler(IllegalArgumentException.class);
        assertSame(expectedIllegalArgumentException, handlerIllegalArgumentException.get());
    }
}