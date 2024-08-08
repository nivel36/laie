package es.nivel36.laie.ejb.job.offer;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.util.Parameters;

public class JobOfferProcessDao extends AbstractDao {
	
	public JobOfferProcess findJobOfferProcessByName(final String name) {
		Objects.requireNonNull(name);
		final String namedQuery = "JobOfferProcess.findJobOfferProcessByName";
		final Parameters parameters = map("name", name);
		return this.findByQuery(JobOfferProcess.class, namedQuery, parameters);
	}
}
