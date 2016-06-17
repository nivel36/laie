package ged.ejb.user.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.CrudDao;
import ged.ejb.core.Repository;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.user.BookmarkDao;
import ged.ejb.user.BookmarkService;
import ged.ejb.user.User;

@Stateless
public class BookmarkServiceImpl extends AbstractService<Long, Bookmark> implements BookmarkService {

	@Inject
	@Repository
	private BookmarkDao dao;

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

	public void setDao(final BookmarkDao dao) {
		this.dao = dao;
	}
}
