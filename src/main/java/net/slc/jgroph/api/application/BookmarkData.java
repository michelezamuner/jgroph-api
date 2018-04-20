package net.slc.jgroph.api.application;

public class BookmarkData
{
    private final int id;
    private final String title;

    public BookmarkData(final int id, final String title)
    {
        this.id = id;
        this.title = title;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }
}