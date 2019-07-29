package ged.ejb.job.offer;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class JobOfferService extends AbstractService<JobOffer> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	public JobOffer newJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		logger.debug("Add new jobOffer {}", jobOffer);
		final JobOfferState state = this.jobOfferDao.findFirstJobOfferState();
		jobOffer.setJobOfferState(state);
		return this.save(jobOffer);
	}

	public List<JobOffer> findJobOffers(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job offers of the candidate  {}", candidate);
		return this.jobOfferDao.findJobOffers(candidate, page);
	}

	public List<JobOffer> findJobOffers(final Client client, Page page) {
		Objects.requireNonNull(client, "Client can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job offers of the client  {}", client);
		return this.jobOfferDao.findJobOffers(client, page);
	}

	public List<JobOffer> findJobOffers(final User owner, final Page page) {
		Objects.requireNonNull(owner, "Owner can't be null ");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findJobOffers(owner, page);
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Find all job offer states");
		return this.jobOfferDao.findJobOfferStates();
	}

	@Override
	public AbstractDao<JobOffer> getDao() {
		return this.jobOfferDao;
	}

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		Objects.requireNonNull(jobOfferDao, "JobOfferDao can't be null");
		this.jobOfferDao = jobOfferDao;
	}
}
