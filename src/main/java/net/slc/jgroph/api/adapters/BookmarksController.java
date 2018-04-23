package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.*;
import net.slc.jgroph.api.infrastructure.http_server.Request;
import net.slc.jgroph.api.infrastructure.http_server.Response;
import net.slc.jgroph.api.infrastructure.http_server.ResponseException;
import net.slc.jgroph.infrastructure.container.Container;

public class BookmarksController
{
    private final Container container;

    public BookmarksController(final Container container)
    {
        this.container = container;
    }

    public void index(final Request request, final Response response)
            throws ResponseException
    {
        final BookmarksPresenter presenter = container.make(BookmarksPresenter.class, response);
        final BookmarksRepository repository = container.make(BookmarksRepository.class);
        final GetAllBookmarks getAllBookmarks = container.make(GetAllBookmarks.class, presenter, repository);

        try {
            getAllBookmarks.perform();
        } catch (PresenterException e) {
            final String message = e.getMessage() == null ? "Error presenting output data." : e.getMessage();
            throw new ResponseException(message, e);
        } catch (RepositoryException e) {
            final String message = e.getMessage() == null ? "Error retrieve data from storage." : e.getMessage();
            throw new ResponseException(message, e);
        }
    }
}