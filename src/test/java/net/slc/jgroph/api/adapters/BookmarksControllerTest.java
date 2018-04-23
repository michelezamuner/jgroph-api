package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.*;
import net.slc.jgroph.api.infrastructure.http_server.Request;
import net.slc.jgroph.api.infrastructure.http_server.Response;
import net.slc.jgroph.api.infrastructure.http_server.ResponseException;
import net.slc.jgroph.infrastructure.container.Container;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class BookmarksControllerTest
{
    @Rule public final ExpectedException exception = ExpectedException.none();
    @Mock private Request request;
    @Mock private Response response;
    @Mock private Container container;
    @Mock private BookmarksPresenter presenter;
    @Mock private BookmarksRepository repository;
    @Mock private GetAllBookmarks getAllBookmarks;
    private BookmarksController controller;

    @Before
    public void setUp()
    {
        when(container.make(BookmarksPresenter.class, response)).thenReturn(presenter);
        when(container.make(BookmarksRepository.class)).thenReturn(repository);
        when(container.make(GetAllBookmarks.class, presenter, repository)).thenReturn(getAllBookmarks);

        controller = new BookmarksController(container);
    }

    @Test
    public void indexCallsUseCaseWithCorrectDependencies()
            throws ResponseException, PresenterException, RepositoryException
    {
        controller.index(request, response);
        verify(getAllBookmarks).perform();
    }

    @Test
    public void convertsPresenterExceptionsToResponseExceptions()
            throws ResponseException, PresenterException, RepositoryException
    {
        final String message = "Error message";
        exception.expect(ResponseException.class);
        exception.expectMessage(message);

        doThrow(new PresenterException(message)).when(getAllBookmarks).perform();

        controller.index(request, response);
    }

    @Test
    public void covertsRepositoryExceptionsToResponseExceptions()
            throws ResponseException, PresenterException, RepositoryException
    {
        final String message = "Error message";
        exception.expect(ResponseException.class);
        exception.expectMessage(message);

        doThrow(new RepositoryException(message)).when(getAllBookmarks).perform();

        controller.index(request, response);
    }
}