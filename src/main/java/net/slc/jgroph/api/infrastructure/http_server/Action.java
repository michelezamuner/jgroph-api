package net.slc.jgroph.api.infrastructure.http_server;

public interface Action
{
    void exec(final Request request, final Response response)
            throws HttpException, ResponseException;
}