package net.slc.jgroph.api.infrastructure.http_server;

class NotFoundException extends HttpException
{
    private static final long serialVersionUID = -7214197510244793073L;

    NotFoundException(final String message)
    {
        super(message);
    }

    NotFoundException(final String message, final Throwable previous)
    {
        super(message, previous);
    }
}