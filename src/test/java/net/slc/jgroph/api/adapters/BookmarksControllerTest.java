package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.GetAllBookmarks;
import net.slc.jgroph.api.infrastructure.Request;
import net.slc.jgroph.api.infrastructure.Response;
import net.slc.jgroph.api.application.BookmarksRepository;
import net.slc.jgroph.infrastructure.container.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class BookmarksControllerTest
{
    @Mock private Request request;
    @Mock private Response response;
    @Mock private Container container;
    @Mock private BookmarksPresenter presenter;
    @Mock private BookmarksRepository repository;
    @Mock private GetAllBookmarks getAllBookmarks;
    @InjectMocks private BookmarksController controller;

    @Test
    public void indexCallsUseCaseWithCorrectDependencies()
    {
        when(container.make(BookmarksPresenter.class, response)).thenReturn(presenter);
        when(container.make(BookmarksRepository.class)).thenReturn(repository);
        when(container.make(GetAllBookmarks.class, presenter, repository)).thenReturn(getAllBookmarks);

        controller.index(request, response);

        verify(getAllBookmarks).perform();
    }
}