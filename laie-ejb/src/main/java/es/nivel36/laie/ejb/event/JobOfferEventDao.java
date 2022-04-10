package es.nivel36.laie.ejb.event;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class JobOfferEventDao extends AbstractDao {

	public void insert(final JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent);
		this.em.persist(jobOfferEvent);
	}

	public JobOfferEvent findById(final String Id) {
		Objects.requireNonNull(Id);
		final String namedQuery = "JobOfferEvent.findById";
		final Parameters parameters = map("Id", Id);
		return this.findByQuery(JobOfferEvent.class, namedQuery, parameters);
	}
}