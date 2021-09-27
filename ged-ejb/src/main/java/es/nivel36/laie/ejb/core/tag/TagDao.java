package es.nivel36.laie.ejb.core.tag;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class TagDao extends AbstractIndexedDao<Tag> {

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label, "Label can't be null");
		try {
			return this.getPersistenceFacade().findByQuery(Tag.class, "Tag.findByLabel", map("label", label));
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	protected Class<Tag> getType() {
		return Tag.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "label" };
	}
}