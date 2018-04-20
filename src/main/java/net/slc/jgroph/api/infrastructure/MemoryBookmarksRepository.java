package net.slc.jgroph.api.infrastructure;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryBookmarksRepository implements BookmarksRepository
{
    @Override
    public List<BookmarkData> getAllBookmarks()
    {
        final List<BookmarkData> data = new ArrayList<>();
        data.add(new BookmarkData(1, "Title 1"));
        data.add(new BookmarkData(2, "Title 2"));

        return data;
    }
}