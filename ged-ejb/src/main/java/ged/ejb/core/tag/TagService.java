package ged.ejb.core.tag;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Stateless
public class TagService extends AbstractIndexedService<Tag> {

	@Inject
	@Repository
	private TagDao tagDao;

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label, "Label can't be null");
		return this.tagDao.findByLabel(label);
	}

	@Override
	protected AbstractIndexedDao<Tag> getDao() {
		return this.tagDao;
	}

	public void setTagDao(final TagDao tagDao) {
		Objects.requireNonNull(tagDao, "Dao can't be null");
		this.tagDao = tagDao;
	}
}