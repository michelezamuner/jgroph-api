package net.slc.jgroph.api.adapters.http_bookmarks_repository;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksRepository;
import net.slc.jgroph.api.application.RepositoryException;
import net.slc.jgroph.api.infrastructure.http_client.Client;
import net.slc.jgroph.api.infrastructure.http_client.ClientException;
import net.slc.jgroph.api.infrastructure.http_client.Response;
import net.slc.jgroph.api.infrastructure.http_client.Url;
import net.slc.jgroph.infrastructure.container.Container;

import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HttpBookmarksRepository implements BookmarksRepository
{
    private final Container container;

    public HttpBookmarksRepository(final Container container)
    {
        this.container = container;
    }

    @Override
    public List<BookmarkData> getAllBookmarks()
            throws RepositoryException
    {
        final Configuration configuration = container.make(Configuration.class);
        final Client client = container.make(Client.class);
        final Response response = getResponse(client, container.make(Url.class, configuration.getRepositoryUrl()));

        return getBookmarksData(response.getBody());
    }

    private Response getResponse(final Client client, final Url url)
            throws RepositoryException
    {
        try {
            return client.sendGet(url);
        } catch (ClientException e) {
            final String message = e.getMessage() == null ? "Error reading remote repository." : e.getMessage();
            throw new RepositoryException(message, e);
        }
    }

    private List<BookmarkData> getBookmarksData(final String responseBody)
    {
        final List<BookmarkData> data = new ArrayList<>();

        final JsonReader reader = Json.createReader(new StringReader(responseBody));
        for (final JsonValue item : reader.readArray()) {
            final JsonObject bookmark = item.asJsonObject();
            data.add(new BookmarkData(bookmark.getInt("id"), bookmark.getString("title")));
        }
        reader.close();

        return data;
    }
}