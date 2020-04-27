package net.slc.jgroph.api.infrastructure.http_server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Factory
{
    Request createRequest(final HttpServletRequest request)
    {
        return new Request(request);
    }

    Response createResponse(final HttpServletResponse response)
    {
        return new Response(response);
    }
}