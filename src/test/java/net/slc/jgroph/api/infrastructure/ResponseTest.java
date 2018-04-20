package net.slc.jgroph.api.infrastructure;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertSame;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class ResponseTest
{
    @Rule public final ExpectedException expectedException = ExpectedException.none();
    @Mock private HttpServletResponse servletResponse;
    @Mock private PrintWriter responseWriter;
    private Response response;

    @Before
    public void setUp()
            throws IOException
    {
        when(servletResponse.getWriter()).thenReturn(responseWriter);
        response = new Response(servletResponse);
    }

    @Test
    public void setsJsonContentType()
    {
        response.setJsonContentType();
        verify(servletResponse).setHeader("Content-Type", "application/json");
    }

    @Test
    public void exposesWriter()
            throws ResponseException
    {
        assertSame(responseWriter, response.getWriter());
    }

    @Test
    public void failsIfCannotGetWriter()
            throws IOException
    {
        expectedException.expect(ResponseException.class);
        when(servletResponse.getWriter()).thenThrow(new IOException());
        response.getWriter();
    }
}