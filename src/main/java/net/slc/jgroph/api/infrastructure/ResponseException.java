package net.slc.jgroph.api.infrastructure;

import java.io.IOException;

public class ResponseException extends IOException
{
    public ResponseException(final String message)
    {
        super(message);
    }

    public ResponseException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}