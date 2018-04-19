package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.PresenterException;
import net.slc.jgroph.api.infrastructure.Response;
import net.slc.jgroph.api.infrastructure.ResponseException;

import java.util.List;

public class ApiBookmarksPresenter implements BookmarksPresenter
{
    private final Response response;

    public ApiBookmarksPresenter(final Response response)
    {
        this.response = response;
    }

    public void displayAllBookmarks(final List<BookmarkData> bookmarksData)
            throws PresenterException
    {
        response.setJsonContentType();
        try {
            response.write("{}");
        } catch (ResponseException e) {
            final String message = e.getMessage() == null ? "Error writing to the response." : e.getMessage();
            throw new PresenterException(message, e);
        }
    }
}