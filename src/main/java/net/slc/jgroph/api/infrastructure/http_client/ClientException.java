package net.slc.jgroph.api.infrastructure.http_client;

public class ClientException extends Exception
{
    private static final long serialVersionUID = 3334700152454657549L;

    public ClientException(final String message)
    {
        super(message);
    }

    public ClientException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}