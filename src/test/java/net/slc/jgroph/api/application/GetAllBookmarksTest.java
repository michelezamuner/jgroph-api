package net.slc.jgroph.api.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class GetAllBookmarksTest
{
    @Mock private BookmarksPresenter presenter;
    @Mock private BookmarksRepository repository;
    @Mock private List<BookmarkData> bookmarksData;
    @InjectMocks private GetAllBookmarks getAllBookmarks;

    @Test
    public void sendsRepositoryDataToPresenter()
    {
        when(repository.getAllBookmarks()).thenReturn(bookmarksData);
        getAllBookmarks.perform();
        verify(presenter).displayAllBookmarks(bookmarksData);
    }
}