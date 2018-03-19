package ged.ejb.job.offer;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Repository
public class JobOfferDaoJpa extends AbstractDaoJpa<JobOffer> implements JobOfferDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Override
	public List<JobOffer> findAllByOwner(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug("Buscando todas las ofertas del usuario {}", owner.getFullName());
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return this.findByQuery(JobOffer.class, "JobOffer.findAllByOwner", parameters, 0, 0);
	}

	@Override
	public List<JobOffer> findJobOffersByClientId(final long clientId) {
		logger.debug("SELECT  job offers by client id {}", clientId);
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("clientId", clientId);
		return this.findByQuery(JobOffer.class, "JobOffer.findByClientId", parameters, 0, 0);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("owner", owner);
		return this.findByQuery(JobOffer.class, "JobOffer.findLastJobOffers", parameters, 0, 0);
	}

	@Override
	public Class<JobOffer> getType() {
		return JobOffer.class;
	}

	@Override
	public List<JobOffer> search(final String searchText) {
		return this.getPersistenceFacade().search(JobOffer.class, searchText, "name", "client.name");
	}
}
