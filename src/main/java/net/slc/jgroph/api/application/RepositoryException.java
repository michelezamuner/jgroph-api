package net.slc.jgroph.api.application;

public class RepositoryException extends Exception
{
    private static final long serialVersionUID = 5244255214284885770L;

    public RepositoryException(final String message)
    {
        super(message);
    }

    public RepositoryException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}