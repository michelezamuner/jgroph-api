package net.slc.jgroph.api.infrastructure.http_server;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Routes
{
    private Map<RequestMethod, Map<String, Action>> actions = new HashMap<>();
    private Map<Class<? extends Throwable>, Handler> handlers = new HashMap<>();

    @SuppressWarnings("dereference.of.nullable") // checker framework doesn't know about Map.putIfAbsent
    public void addAction(final RequestMethod method, final String pathRegex, final Action action)
    {
        actions.putIfAbsent(method, new HashMap<>());
        actions.get(method).put(pathRegex, action);
    }

    public void addHandler(final Class<? extends Throwable> type, final Handler handler)
    {
        handlers.put(type, handler);
    }

    Optional<Action> getAction(final RequestMethod method, final String path)
    {
        final Map<String, Action> actionsOfMethod = actions.get(method);
        if (actionsOfMethod == null) {
            return Optional.empty();
        }

        for (final Map.Entry<String, Action> entry : actionsOfMethod.entrySet()) {
            if (path.matches(entry.getKey())) {
                return Optional.ofNullable(entry.getValue());
            }
        }

        return Optional.empty();
    }

    Optional<Handler> getHandler(final Class<? extends Throwable> type)
    {
        return Optional.ofNullable(handlers.get(type));
    }
}