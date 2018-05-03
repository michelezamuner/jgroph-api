package net.slc.jgroph.api.adapters.http_bookmarks_repository;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksRepository;
import net.slc.jgroph.api.application.RepositoryException;
import net.slc.jgroph.api.infrastructure.http_client.Client;
import net.slc.jgroph.api.infrastructure.http_client.ClientException;
import net.slc.jgroph.api.infrastructure.http_client.Response;

import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HttpBookmarksRepository implements BookmarksRepository
{
    private final Client client;
    private final Configuration configuration;

    public HttpBookmarksRepository(final Client client, final Configuration configuration)
    {
        this.client = client;
        this.configuration = configuration;
    }

    @Override
    public List<BookmarkData> getAllBookmarks()
            throws RepositoryException
    {
        final Response response = getResponse(client, configuration.getRepositoryUrl());

        return getBookmarksData(response.getBody());
    }

    private Response getResponse(final Client client, final String url)
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