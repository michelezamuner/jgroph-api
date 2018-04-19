package net.slc.jgroph.api.infrastructure;

import java.io.IOException;

public class ResponseException extends IOException
{
    private static final long serialVersionUID = 1836955718940582006L;

    public ResponseException(final String message)
    {
        super(message);
    }

    public ResponseException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}