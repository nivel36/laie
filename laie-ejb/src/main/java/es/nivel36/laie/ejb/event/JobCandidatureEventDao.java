package es.nivel36.laie.ejb.event;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class JobCandidatureEventDao extends AbstractDao {

	public void insert(JobCandidatureEvent event) {
		Objects.requireNonNull(event);
		this.insert(event);
	}
}
