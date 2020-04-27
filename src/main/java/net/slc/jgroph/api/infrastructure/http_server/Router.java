package net.slc.jgroph.api.infrastructure.http_server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="router", urlPatterns={"/"}, loadOnStartup=1)
public class Router extends HttpServlet
{
    private static final long serialVersionUID = 3865918382485189944L;

    private final Routes routes;
    private final Factory factory;

    public Router(final Routes routes, final Factory factory)
    {
        this.routes = routes;
        this.factory = factory;
    }

    @Override
    protected void service(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException
    {
        final Request request = factory.createRequest(servletRequest);
        final Response response = factory.createResponse(servletResponse);

        try {
            routes.getAction(request.getMethod(), request.getPath())
                    .orElseThrow(() -> new NotFoundException("No route found for " + request.getPath()))
                    .exec(request, response);
        } catch (HttpException e) {
            final String message = e.getMessage() == null ? "Unexpected empty exception message" : e.getMessage();
            routes.getHandler(e.getClass())
                    .orElseThrow(() -> new ServletException(message))
                    .handle(request, response, e);
        }
    }
}