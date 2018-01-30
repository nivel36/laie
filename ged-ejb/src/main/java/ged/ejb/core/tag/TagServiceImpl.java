package ged.ejb.core.tag;

import java.util.Objects;

import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

public class TagServiceImpl extends AbstractService<Tag> implements TagService {

	private final TagDao tagDao;

	@Inject
	public TagServiceImpl(@Repository final TagDao tagDao) {
		Objects.requireNonNull(tagDao);
		this.tagDao = tagDao;
	}

	@Override
	protected Dao<Tag> getDao() {
		return this.tagDao;
	}
}