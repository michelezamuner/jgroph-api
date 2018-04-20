package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.PresenterException;
import net.slc.jgroph.api.infrastructure.Response;
import net.slc.jgroph.api.infrastructure.ResponseException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiBookmarksPresenter implements BookmarksPresenter
{
    private final Response response;

    public ApiBookmarksPresenter(final Response response)
    {
        this.response = response;
    }

    public void displayAllBookmarks(final List<BookmarkData> bookmarksData)
            throws PresenterException
    {
        response.setJsonContentType();

        final JsonArray json = createJson(bookmarksData);

        try {
            writeJson(json);
        } catch (ResponseException e) {
            final String message = e.getMessage() == null ? "Error writing to the response." : e.getMessage();
            throw new PresenterException(message, e);
        }
    }

    private JsonArray createJson(final List<BookmarkData> bookmarksData)
    {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final BookmarkData bookmark : bookmarksData) {
            JsonObject bookmarkJson = Json.createObjectBuilder()
                    .add("id", bookmark.getId())
                    .add("title", bookmark.getTitle())
                    .build();
            builder.add(bookmarkJson);
        }

        return builder.build();
    }

    private void writeJson(final JsonArray json)
            throws ResponseException
    {
        final Map<String, Object> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);

        final JsonWriter writer = Json.createWriterFactory(config).createWriter(response.getWriter());
        writer.writeArray(json);
        writer.close();
    }
}