package net.slc.jgroph.api.application;

public class PresenterException extends Exception
{
    private static final long serialVersionUID = 3702297705062639602L;

    public PresenterException(final String message)
    {
        super(message);
    }

    public PresenterException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}