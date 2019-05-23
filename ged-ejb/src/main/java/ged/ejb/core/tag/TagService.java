package ged.ejb.core.tag;

import java.util.Objects;

import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

public class TagService extends AbstractService<Tag> {

	@Inject
	@Repository
	private TagDao tagDao;

	@Override
	protected AbstractDao<Tag> getDao() {
		return this.tagDao;
	}

	public void setTagDao(final TagDao tagDao) {
		Objects.requireNonNull(tagDao, "Dao can't be null");
		this.tagDao = tagDao;
	}
	
	public Tag findByName(String name) {
		Objects.requireNonNull(name, "Name can't be null");
		return tagDao.findByName(name);
	}
}