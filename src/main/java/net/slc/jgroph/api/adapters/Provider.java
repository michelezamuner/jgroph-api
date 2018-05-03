package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.BookmarksRepository;
import net.slc.jgroph.api.infrastructure.http_server.Response;
import net.slc.jgroph.api.infrastructure.http_server.Routes;
import net.slc.jgroph.api.infrastructure.http_server.Server;
import net.slc.jgroph.infrastructure.container.Callback;
import net.slc.jgroph.infrastructure.container.Container;

import java.net.InetSocketAddress;

class Provider
{
    void register(final Container container)
    {
        container.bind(Server.class, (Callback)(args) ->
                new Server(new org.eclipse.jetty.server.Server((InetSocketAddress)args[0])));
        container.bind(Routes.class, new Routes(BookmarksController.class));
        container.bind(BookmarksPresenter.class, (Callback)(args) ->
                new ApiBookmarksPresenter((Response)args[0]));
        container.bind(BookmarksRepository.class, container.make(MemoryBookmarksRepository.class));
    }
}