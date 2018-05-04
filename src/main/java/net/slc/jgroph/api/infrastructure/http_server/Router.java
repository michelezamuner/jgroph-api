package net.slc.jgroph.api.infrastructure.http_server;

import net.slc.jgroph.infrastructure.container.Container;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet(name = "router", urlPatterns = {"/"}, loadOnStartup = 1)
public class Router extends HttpServlet
{
    private static final long serialVersionUID = -838403813674767401L;

    private final Container container;
    private final Routes routes;
    private final ActionResolver resolver;

    public Router(final Container container, final Routes routes)
    {
        this.container = container;
        this.routes = routes;
        this.resolver = this.container.make(ActionResolver.class);
    }

    @Override
    protected void doGet(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException
    {
        final Request request = container.make(Request.class, servletRequest);
        final Response response = container.make(Response.class, servletResponse);

        try {
            final Route route = routes.get(request);
            executeAction(route, request, response);
        } catch (RouteNotFoundException e) {
            throw new ServletException("Only /bookmarks/ is currently supported.", e);
        }
    }

    private void executeAction(final Route route, final Request request, final Response response)
            throws ServletException, IOException
    {
        final Object controller = container.make(route.getController());
        try {
            final Method action =
                    resolver.resolve(controller.getClass(), route.getAction(), Request.class, Response.class);
            action.invoke(controller, request, response);
        } catch (InvocationTargetException e) {
            final String originalMessage = e.getCause() == null ? null : e.getCause().getMessage();
            final String message = originalMessage == null ? "Error executing action." : originalMessage;
            throw new IOException(message, e);
        } catch (ReflectiveOperationException e) {
            throw new ServletException("Invalid action for controller.", e);
        }
    }
}