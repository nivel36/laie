package ged.ejb.core.tag;

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
		this.tagDao = tagDao;
	}
}