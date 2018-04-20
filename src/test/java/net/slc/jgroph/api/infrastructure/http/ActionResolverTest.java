package net.slc.jgroph.api.infrastructure.http;

import net.slc.jgroph.api.adapters.BookmarksController;
import net.slc.jgroph.api.infrastructure.http.ActionResolver;
import net.slc.jgroph.api.infrastructure.http.Request;
import net.slc.jgroph.api.infrastructure.http.Response;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class ActionResolverTest
{
    @Test
    public void resolvesActionOfGivenController()
            throws NoSuchMethodException
    {
        final ActionResolver resolver = new ActionResolver();
        final Method action = resolver.resolve(BookmarksController.class, "index", Request.class, Response.class);
        final Method expected = BookmarksController.class.getMethod("index", Request.class, Response.class);
        assertEquals(expected, action);
    }
}