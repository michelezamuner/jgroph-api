package net.slc.jgroph.api.application;

import java.util.List;

public interface BookmarksPresenter
{
    void displayAllBookmarks(final List<BookmarkData> bookmarksData)
        throws PresenterException;
}