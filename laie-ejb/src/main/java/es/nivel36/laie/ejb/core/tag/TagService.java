package es.nivel36.laie.ejb.core.tag;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class TagService {

	@Inject
	@Repository
	private TagDao tagDao;

	private static final Logger logger = LoggerFactory.getLogger(TagService.class);

	public void setTagDao(final TagDao tagDao) {
		Objects.requireNonNull(tagDao, "Dao can't be null");
		this.tagDao = tagDao;
	}
}