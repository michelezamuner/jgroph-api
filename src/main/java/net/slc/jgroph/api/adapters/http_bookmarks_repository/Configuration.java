package net.slc.jgroph.api.adapters.http_bookmarks_repository;

public class Configuration
{
    public String getRepositoryUrl()
    {
        final String env = System.getenv("JGROPH_BOOKMARKS_SERVICES_API_URL");
        return env == null ? "" : env;
    }
}