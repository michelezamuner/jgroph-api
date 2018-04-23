package net.slc.jgroph.api.adapters.http_bookmarks_repository;

import com.github.javafaker.Faker;
import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.RepositoryException;
import net.slc.jgroph.api.infrastructure.http_client.Client;
import net.slc.jgroph.api.infrastructure.http_client.ClientException;
import net.slc.jgroph.api.infrastructure.http_client.Response;
import net.slc.jgroph.api.infrastructure.http_client.Url;
import net.slc.jgroph.infrastructure.container.Container;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertSame;

import java.util.List;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class HttpBookmarksRepositoryTest
{
    @Rule public ExpectedException exception = ExpectedException.none();
    private final Faker faker = new Faker();
    @Mock private Url url;
    @Mock private Client client;
    @Mock private Configuration configuration;
    @Mock private Container container;
    private HttpBookmarksRepository repository;

    @Before
    public void setUp()
    {
        final String urlString = faker.internet().url();
        when(configuration.getRepositoryUrl()).thenReturn(urlString);
        when(container.make(Url.class, urlString)).thenReturn(url);
        when(container.make(Client.class)).thenReturn(client);
        when(container.make(Configuration.class)).thenReturn(configuration);
        repository = new HttpBookmarksRepository(container);
    }

    @Test
    public void sendsRequestsToConfiguredUrl()
            throws ClientException, RepositoryException
    {
        when(client.sendGet(url)).thenReturn(new Response(
                200,
                "application/json",
                "[]"
        ));
        repository.getAllBookmarks();
        verify(client).sendGet(url);
    }

    @Test
    public void translatesJsonToBookmarksData()
            throws ClientException, RepositoryException
    {
        final String response = "[\n"
                + "    {\n"
                + "        \"id\": 1,\n"
                + "        \"title\": \"Title 1\"\n"
                + "    },\n"
                + "    {\n"
                + "        \"id\": 2,\n"
                + "        \"title\": \"Title 2\"\n"
                + "    }\n"
                + "]";
        when(client.sendGet(url)).thenReturn(new Response(
                200,
                "application/json",
                response
        ));
        final List<BookmarkData> data = repository.getAllBookmarks();
        assertSame(2, data.size());

        final BookmarkData first = data.get(0);
        assertEquals(1, first.getId());
        assertEquals("Title 1", first.getTitle());

        final BookmarkData second = data.get(1);
        assertEquals(2, second.getId());
        assertEquals("Title 2", second.getTitle());
    }

    @Test
    public void failsWhenUnableToGetData()
            throws ClientException, RepositoryException
    {
        final String message = "Error message.";
        exception.expect(RepositoryException.class);
        exception.expectMessage(message);

        when(client.sendGet(url)).thenThrow(new ClientException(message));

        repository.getAllBookmarks();
    }
}