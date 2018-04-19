package net.slc.jgroph.api.infrastructure;

import net.slc.jgroph.infrastructure.container.Container;
import org.checkerframework.checker.nullness.qual.Nullable;

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

    public Router()
    {
        this(null, null);
    }

    public Router(@Nullable final Container container, @Nullable final Routes routes)
    {
        this.container = container == null ? new Container() : container;
        this.routes = routes == null ? new Routes() : routes;
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
            throws ServletException
    {
        final Object controller = container.make(route.getController());
        try {
            final Method action = controller.getClass().getMethod(route.getAction(), Request.class, Response.class);
            action.invoke(controller, request, response);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ServletException("Invalid action for controller.", e);
        }
    }
}