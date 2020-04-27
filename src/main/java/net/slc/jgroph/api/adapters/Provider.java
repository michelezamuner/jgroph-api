package net.slc.jgroph.api.adapters;

import net.slc.jgroph.api.adapters.http_bookmarks_repository.HttpBookmarksRepository;
import net.slc.jgroph.api.application.BookmarksPresenter;
import net.slc.jgroph.api.application.BookmarksRepository;
import net.slc.jgroph.api.infrastructure.http_server.RequestMethod;
import net.slc.jgroph.api.infrastructure.http_server.Response;
import net.slc.jgroph.api.infrastructure.http_server.Routes;
import net.slc.jgroph.infrastructure.container.Callback;
import net.slc.jgroph.infrastructure.container.Container;

class Provider
{
    void register(final Container container)
    {
        final Routes routes = container.make(Routes.class);
        container.bind(Routes.class, routes);
        final BookmarksController controller = container.make(BookmarksController.class);
        routes.addAction(RequestMethod.GET, "/bookmarks/?.*", controller::index);
        container.bind(BookmarksPresenter.class, (Callback)(args) ->
                new ApiBookmarksPresenter((Response)args[0]));
        container.bind(BookmarksRepository.class, container.make(HttpBookmarksRepository.class));
    }
}