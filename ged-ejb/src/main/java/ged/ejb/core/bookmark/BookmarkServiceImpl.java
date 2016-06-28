package ged.ejb.core.bookmark;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.CrudDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class BookmarkServiceImpl extends AbstractService<Long, Bookmark> implements BookmarkService {

	@Inject
	@Repository
	private BookmarkDao dao;

	@Override
	public void delete(final String url) {
		final Bookmark bookmark = this.dao.findByUrl(url);
		delete(bookmark);
	}

	@Override
	public List<Bookmark> findAllByUser(final User user) {
		return this.dao.findAllByUser(user);
	}

	@Override
	public Bookmark findByUrl(final String url) {
		return this.dao.findByUrl(url);
	}

	@Override
	protected CrudDao<Long, Bookmark> getDao() {
		return this.dao;
	}
}
