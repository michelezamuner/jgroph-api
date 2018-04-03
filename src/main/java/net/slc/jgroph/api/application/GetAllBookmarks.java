package net.slc.jgroph.api.application;

public class GetAllBookmarks
{
    private final BookmarksPresenter presenter;
    private final BookmarksRepository repository;

    public GetAllBookmarks(final BookmarksPresenter presenter, final BookmarksRepository repository)
    {
        this.presenter = presenter;
        this.repository = repository;
    }

    public void perform()
    {
        presenter.displayAllBookmarks(repository.getAllBookmarks());
    }
}