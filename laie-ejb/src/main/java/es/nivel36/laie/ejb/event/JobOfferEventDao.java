package es.nivel36.laie.ejb.event;

import static es.nivel36.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Repository;
import es.nivel36.core.util.Parameters;

@Repository
public class JobOfferEventDao extends AbstractDao {

	public void insert(final JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent);
		this.em.persist(jobOfferEvent);
	}

	public JobOfferEvent findByUid(final String uid) {
		Objects.requireNonNull(uid);
		final String namedQuery = "JobOfferEvent.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(JobOfferEvent.class, namedQuery, parameters);
	}
}