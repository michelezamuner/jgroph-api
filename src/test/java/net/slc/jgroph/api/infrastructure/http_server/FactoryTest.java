package net.slc.jgroph.api.infrastructure.http_server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class FactoryTest
{
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    private Factory factory;

    @Before
    public void setUp()
    {
        factory = new Factory();
    }

    @Test
    public void createsRequests()
    {
        final Request expected = new Request(request);
        assertEquals(expected, factory.createRequest(request));
    }

    @Test
    public void createsResponses()
    {
        final Response expected = new Response(response);
        assertEquals(expected, factory.createResponse(response));
    }
}