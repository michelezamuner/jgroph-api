package net.slc.jgroph.api.infrastructure;

import java.lang.reflect.Method;

/**
 * This wrapper is needed to be able to unit test resolutions of controller actions, since Class is final, and thus
 * cannot be mocked.
 */
public class ActionResolver
{
    public Method resolve(final Class<?> controllerClass, final String action, final Class<?>... paramTypes)
            throws NoSuchMethodException
    {
        return controllerClass.getMethod(action, paramTypes);
    }
}