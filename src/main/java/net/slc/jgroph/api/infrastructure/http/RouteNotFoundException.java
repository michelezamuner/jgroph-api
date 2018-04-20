package net.slc.jgroph.api.infrastructure.http;

class RouteNotFoundException extends Exception
{
    private static final long serialVersionUID = -808395768373601341L;

    RouteNotFoundException(final String message)
    {
        super(message);
    }

    RouteNotFoundException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}