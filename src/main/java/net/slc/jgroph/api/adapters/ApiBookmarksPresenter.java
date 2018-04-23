package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarkData;
import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.PresenterException;
import net.slc.jgroph.api.infrastructure.http_server.Response;
import net.slc.jgroph.api.infrastructure.http_server.ResponseException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.io.Writer;
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

        final String json = createJson(bookmarksData);

        try {
            response.write(json);
        } catch (ResponseException e) {
            final String message = e.getMessage() == null ? "Error writing to the response." : e.getMessage();
            throw new PresenterException(message, e);
        }
    }

    private String createJson(final List<BookmarkData> bookmarksData)
    {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final BookmarkData bookmark : bookmarksData) {
            JsonObject bookmarkJson = Json.createObjectBuilder()
                    .add("id", bookmark.getId())
                    .add("title", bookmark.getTitle())
                    .build();
            builder.add(bookmarkJson);
        }

        final Map<String, Object> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);

        final Writer stringWriter = new StringWriter();
        final JsonWriter writer = Json.createWriterFactory(config).createWriter(stringWriter);
        writer.writeArray(builder.build());
        writer.close();

        return stringWriter.toString().trim();
    }
}