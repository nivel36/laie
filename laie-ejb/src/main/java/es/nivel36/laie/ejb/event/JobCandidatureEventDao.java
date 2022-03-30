package es.nivel36.laie.ejb.event;

import java.util.Objects;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Repository;

@Repository
public class JobCandidatureEventDao extends AbstractDao {

	public void insert(final JobCandidatureEvent event) {
		Objects.requireNonNull(event);
		this.em.persist(event);
	}
}
