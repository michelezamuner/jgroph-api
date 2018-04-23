package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.PresenterException;
import net.slc.jgroph.api.infrastructure.http_server.Response;
import net.slc.jgroph.api.infrastructure.http_server.ResponseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class ApiBookmarksPresenterTest
{
    @Rule public final ExpectedException exception = ExpectedException.none();
    @Mock private Response response;
    private ApiBookmarksPresenter presenter;

    @Before
    public void setUp()
    {
        presenter = new ApiBookmarksPresenter(response);
    }

    @Test
    public void usesJsonContentType()
            throws PresenterException
    {
        presenter.displayAllBookmarks(new ArrayList<>());
        verify(response).setJsonContentType();
    }

    @Test
    public void convertsResponseExceptionsToPresenterExceptions()
            throws ResponseException, PresenterException
    {
        final String message = "Error message";
        exception.expect(PresenterException.class);
        exception.expectMessage(message);

        doThrow(new ResponseException(message)).when(response).write(anyString());

        presenter.displayAllBookmarks(new ArrayList<>());
    }

    @Test
    public void convertsEmptyBookmarksDataToJson()
            throws PresenterException, ResponseException
    {
        presenter.displayAllBookmarks(new ArrayList<>());
        verify(response).write("[\n]");
    }

    @Test
    public void convertsComplexBookmarksDataToJson()
            throws PresenterException, ResponseException
    {
        final List<BookmarkData> data = new ArrayList<>();
        data.add(new BookmarkData(1, "Title 1"));
        data.add(new BookmarkData(2, "Title 2"));

        presenter.displayAllBookmarks(data);

        final String expected = "[\n"
                + "    {\n"
                + "        \"id\": 1,\n"
                + "        \"title\": \"Title 1\"\n"
                + "    },\n"
                + "    {\n"
                + "        \"id\": 2,\n"
                + "        \"title\": \"Title 2\"\n"
                + "    }\n"
                + "]";
        verify(response).write(expected);
    }
}