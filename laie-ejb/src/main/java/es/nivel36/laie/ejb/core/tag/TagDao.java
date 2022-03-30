package es.nivel36.laie.ejb.core.tag;

import static es.nivel36.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Repository;
import es.nivel36.core.util.Parameters;

@Repository
public class TagDao extends AbstractDao {

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label);
		final String namedQuery = "Tag.findByLabel";
		final Parameters parameters = map("label", label);
		try {
			return this.findByQuery(Tag.class, namedQuery, parameters);
		} catch (NoResultException e) {
			return null;
		}
	}
}