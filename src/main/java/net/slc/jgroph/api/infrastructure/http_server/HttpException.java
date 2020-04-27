package net.slc.jgroph.api.infrastructure.http_server;

public class HttpException extends Exception
{
    private static final long serialVersionUID = 2896187433688016652L;

    HttpException(final String message)
    {
        super(message);
    }

    HttpException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}