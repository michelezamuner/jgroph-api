package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("initialization")
public class MemoryBookmarksRepositoryTest
{
    private MemoryBookmarksRepository repository;

    @Before
    public void setUp()
    {
        repository = new MemoryBookmarksRepository();
    }

    @Test
    public void isAdapterOfBookmarksRepository()
    {
        assertTrue(repository instanceof BookmarksRepository);
    }

    @Test
    public void returnsStaticData()
    {
        final List<BookmarkData> data = repository.getAllBookmarks();
        assertSame(2, data.size());
        assertSame(1, data.get(0).getId());
        assertSame("Title 1", data.get(0).getTitle());
        assertSame(2, data.get(1).getId());
        assertSame("Title 2", data.get(1).getTitle());
    }
}