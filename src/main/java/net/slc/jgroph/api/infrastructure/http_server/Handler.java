package net.slc.jgroph.api.infrastructure.http_server;

interface Handler
{
    void handle(final Request request, final Response response, final HttpException e)
            throws ResponseException;
}