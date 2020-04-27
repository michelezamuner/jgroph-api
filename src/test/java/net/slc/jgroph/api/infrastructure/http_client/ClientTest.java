package net.slc.jgroph.api.infrastructure.http_client;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class ClientTest
{
    @Rule public ExpectedException exception = ExpectedException.none();
    private final Faker faker = new Faker();
    @Mock private ConnectionHandler connectionHandler;
    @Mock private HttpURLConnection connection;
    private Client client;

    @Before
    public void setUp()
            throws IOException
    {
        when(connectionHandler.open(anyString())).thenReturn(connection);

        client = new Client(connectionHandler);
    }

    @Test
    public void getsResponsesOfGETRequests()
            throws ClientException, IOException
    {
        final String url = faker.internet().url();
        final int responseCode = faker.number().numberBetween(200, 500);
        final String contentType = faker.lorem().word();
        final String body = faker.lorem().paragraph();

        when(connection.getResponseCode()).thenReturn(responseCode);
        when(connection.getContentType()).thenReturn(contentType);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(body.getBytes(UTF_8)));

        final Response response = client.sendGet(url);

        verify(connectionHandler).open(url);
        verify(connection).setRequestMethod("GET");
        assertEquals(responseCode, response.getResponseCode());
        assertSame(contentType, response.getContentType());
        assertEquals(body, response.getBody());
    }

    @Test
    public void failsIfInvalidUrl()
            throws ClientException
    {
        exception.expect(ClientException.class);
        exception.expectMessage("invalid url");

        final Client client = new Client();
        client.sendGet("some invalid url");
    }

    @Test
    public void failsIfErrorOpeningConnection()
            throws ClientException, IOException
    {
        final String message = faker.lorem().sentence();
        exception.expect(ClientException.class);
        exception.expectMessage(message);

        final String url = faker.internet().url();
        when(connectionHandler.open(url)).thenThrow(new IOException(message));

        client.sendGet(url);
    }

    @Test
    public void failsIfErrorSettingRequestMethod()
            throws ClientException, ProtocolException
    {
        final String message = faker.lorem().sentence();
        exception.expect(ClientException.class);
        exception.expectMessage(message);

        doThrow(new ProtocolException(message)).when(connection).setRequestMethod(anyString());

        client.sendGet(faker.internet().url());
    }

    @Test
    public void failsIfErrorGettingResponseCode()
            throws ClientException, IOException
    {
        final String message = faker.lorem().sentence();
        exception.expect(ClientException.class);
        exception.expectMessage(message);

        when(connection.getResponseCode()).thenThrow(new IOException(message));

        client.sendGet(faker.internet().url());
    }

    @Test
    public void failsIfErrorOpeningResponseBodyInputStream()
            throws ClientException, IOException
    {
        final String message = faker.lorem().sentence();
        exception.expect(ClientException.class);
        exception.expectMessage(message);

        when(connection.getInputStream()).thenThrow(new IOException(message));

        client.sendGet(faker.internet().url());
    }

    @Test
    public void failsIfErrorReadingResponseBodyContent()
            throws ClientException, IOException
    {
        exception.expect(ClientException.class);
        exception.expectMessage("Underlying input stream returned zero bytes");

        when(connection.getInputStream()).thenReturn(mock(InputStream.class));

        client.sendGet(faker.internet().url());
    }

    @Test
    public void failsIfErrorClosingResponseBodyInputStream()
            throws ClientException, IOException
    {
        final String message = faker.lorem().sentence();
        exception.expect(ClientException.class);
        exception.expectMessage(message);

        final InputStream stream = mock(InputStream.class);
        doThrow(new IOException(message)).when(stream).close();

        when(connection.getInputStream()).thenReturn(stream);

        client.sendGet(faker.internet().url());
    }
}