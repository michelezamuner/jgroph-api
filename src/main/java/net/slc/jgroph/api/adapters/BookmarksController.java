package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.BookmarksRepository;
import net.slc.jgroph.api.application.GetAllBookmarks;
import net.slc.jgroph.api.infrastructure.Request;
import net.slc.jgroph.api.infrastructure.Response;
import net.slc.jgroph.infrastructure.container.Container;

public class BookmarksController
{
    private final Container container;

    public BookmarksController(final Container container)
    {
        this.container = container;
    }

    public void index(final Request request, final Response response)
    {
        final BookmarksPresenter presenter = container.make(BookmarksPresenter.class, response);
        final BookmarksRepository repository = container.make(BookmarksRepository.class);
        final GetAllBookmarks getAllBookmarks = container.make(GetAllBookmarks.class, presenter, repository);
        getAllBookmarks.perform();
    }
}