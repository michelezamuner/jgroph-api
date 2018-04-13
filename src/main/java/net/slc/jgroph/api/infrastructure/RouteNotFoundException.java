package net.slc.jgroph.api.infrastructure;

class RouteNotFoundException extends Exception
{
    RouteNotFoundException(final String message)
    {
        super(message);
    }

    RouteNotFoundException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}