package ged.ejb.user;

import javax.ejb.Local;

import ged.ejb.core.CrudDao;
import ged.ejb.core.bookmark.Bookmark;

@Local
public interface BookmarkDao extends CrudDao<Long, Bookmark> {

	Bookmark findByUrl(String url);
}