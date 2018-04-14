package ged.ejb.job.offer;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.List;
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
		return this.findByQuery(JobOffer.class, "JobOffer.findAllByOwner", map("owner", owner), 0, 0);
	}

	@Override
	public List<JobOffer> findJobOffersByCandidateId(final long candidateId) {
		logger.debug("SELECT  job offers by candidate id {}", candidateId);
		return this.findByQuery(JobOffer.class, "JobOffer.findByCandidateId", map("candidateId", candidateId), 0, 0);
	}

	@Override
	public List<JobOffer> findJobOffersByClientId(final long clientId) {
		logger.debug("SELECT  job offers by client id {}", clientId);
		return this.findByQuery(JobOffer.class, "JobOffer.findByClientId", map("clientId", clientId), 0, 0);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		return this.findByQuery(JobOffer.class, "JobOffer.findLastJobOffers", map("owner", owner), 0, 0);
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
