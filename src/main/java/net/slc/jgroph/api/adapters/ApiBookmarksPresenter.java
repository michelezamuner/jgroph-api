package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.infrastructure.Response;

import java.util.List;

public class ApiBookmarksPresenter implements BookmarksPresenter
{
    private final Response response;

    public ApiBookmarksPresenter(final Response response)
    {
        this.response = response;
    }

    public void displayAllBookmarks(final List<BookmarkData> bookmarksData)
    {
        response.setJsonContentType();
    }
}