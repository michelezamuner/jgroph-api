package net.slc.jgroph.api.infrastructure.http;

import org.checkerframework.checker.nullness.qual.NonNull;

class Route
{
    private Class<@NonNull ?> controller;

    Route(final Class<@NonNull ?> controller)
    {
        this.controller = controller;
    }

    Class<@NonNull ?> getController()
    {
        return controller;
    }

    String getAction()
    {
        return "index";
    }
}