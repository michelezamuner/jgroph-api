package net.slc.jgroph.api.infrastructure.http_server;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Routes
{
    private final Class<@NonNull ?>[] controllers;

    public Routes(final Class<@NonNull ?> ...controllers)
    {
        this.controllers = controllers;
    }

    Route get(final Request request)
            throws RouteNotFoundException
    {
        if ("/bookmarks/".equals(request.getPath())) {
            return new Route(controllers[0]);
        }

        throw new RouteNotFoundException(String.format("No route found for %s.", request.getPath()));
    }
}