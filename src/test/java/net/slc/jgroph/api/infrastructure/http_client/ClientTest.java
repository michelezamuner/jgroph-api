package net.slc.jgroph.api.infrastructure.http_client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertSame;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class ClientTest
{
    @Rule public ExpectedException exception = ExpectedException.none();
    @Mock private HttpURLConnection connection;
    @Mock private Url url;
    private Client client;

    @Before
    public void setUp()
            throws IOException
    {
        final String response = "Hello, World!";
        final InputStream responseStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

        when(url.openConnection()).thenReturn(connection);
        when(connection.getInputStream()).thenReturn(responseStream);

        client = new Client();
    }

    @Test
    public void sendsGetRequests()
            throws IOException, ClientException
    {
        when(connection.getResponseCode()).thenReturn(200);
        when(connection.getContentType()).thenReturn("text/plain");
        
        final Response response = client.sendGet(url);

        verify(connection).setRequestMethod("GET");

        assertEquals(200, response.getResponseCode());      // Because of autoboxing
        assertSame("text/plain", response.getContentType());
        assertEquals("Hello, World!", response.getBody());
    }

    @Test
    public void failsIfCannotOpenConnection()
            throws IOException, ClientException
    {
        final String message = "Error message.";
        exception.expect(ClientException.class);
        exception.expectMessage(message);

        when(url.openConnection()).thenThrow(new IOException(message));

        client.sendGet(url);
    }
}