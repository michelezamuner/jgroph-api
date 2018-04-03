package net.slc.jgroph.api.application;

import java.util.List;

public interface BookmarksRepository
{
    List<BookmarkData> getAllBookmarks();
}