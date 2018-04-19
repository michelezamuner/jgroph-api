package net.slc.jgroph.api.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private GetAllBookmarks getAllBookmarks;

    @Before
    public void setUp()
    {
        getAllBookmarks = new GetAllBookmarks(presenter, repository);
    }

    @Test
    public void sendsRepositoryDataToPresenter()
            throws PresenterException
    {
        when(repository.getAllBookmarks()).thenReturn(bookmarksData);
        getAllBookmarks.perform();
        verify(presenter).displayAllBookmarks(bookmarksData);
    }
}