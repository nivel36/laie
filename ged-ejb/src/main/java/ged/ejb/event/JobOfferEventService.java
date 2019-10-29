package ged.ejb.event;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.GedSecurityContext;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.job.offer.JobOfferState;
import ged.ejb.user.User;

@Stateless
public class JobOfferEventService extends AbstractService<JobOfferEvent> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private JobOfferEventDao jobOfferEventDao;
	
	@Inject
	private JobOfferService jobOfferService;

	@Inject
	private GedSecurityContext gedSecurityContext;

	public JobOfferEvent createEvent(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer, "Job offer can't be null");
		logger.debug("Create event for job offer {}", jobOffer);

		final User user = this.gedSecurityContext.getLoggedUser();
		final JobOfferEvent jobOfferEvent = new JobOfferEvent(user, jobOffer);
		return this.jobOfferEventDao.save(jobOfferEvent);
	}

	@Override
	protected AbstractDao<JobOfferEvent> getDao() {
		return jobOfferEventDao;
	}

	public JobOfferEvent save(JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent, "Job offer event can't be null");
		logger.debug("Save jobOffer event {}", jobOfferEvent);

		final JobOffer jobOffer = jobOfferEvent.getJobOffer();
		final JobOfferState state = jobOfferEvent.getState();
		jobOfferService.updateJobOfferState(jobOffer, state, null, null);
		return this.jobOfferEventDao.save(jobOfferEvent);
	}

	public void setJobOfferEventDao(JobOfferEventDao jobOfferEventDao) {
		Objects.requireNonNull(jobOfferEventDao);
		this.jobOfferEventDao = jobOfferEventDao;
	}
	
	public void setJobOfferService(JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
	
	public void setSecurityContext(GedSecurityContext gedSecurityContext) {
		Objects.requireNonNull(gedSecurityContext);
		this.gedSecurityContext = gedSecurityContext;
	}
}
