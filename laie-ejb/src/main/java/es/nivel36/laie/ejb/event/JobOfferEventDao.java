package es.nivel36.laie.ejb.event;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class JobOfferEventDao extends AbstractDao {

	public void insert(final JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent);
		this.em.persist(jobOfferEvent);
	}
}