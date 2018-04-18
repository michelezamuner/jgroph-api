package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksRepository;

import java.util.ArrayList;
import java.util.List;

public class HttpBookmarksRepository implements BookmarksRepository
{
    @Override
    public List<BookmarkData> getAllBookmarks() {
        return new ArrayList<>();
    }
}