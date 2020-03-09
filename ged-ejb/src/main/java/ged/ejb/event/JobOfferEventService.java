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
import ged.ejb.user.User;

@Stateless
public class JobOfferEventService extends AbstractService<JobOfferEvent> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private GedSecurityContext gedSecurityContext;

	@Inject
	@Repository
	private JobOfferEventDao jobOfferEventDao;

	@Inject
	private JobOfferService jobOfferService;

	public JobOfferEvent createEvent(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer, "Job offer can't be null");
		logger.debug("Create event for job offer {}", jobOffer);

		final User user = this.gedSecurityContext.getLoggedUser();
		final JobOfferEvent jobOfferEvent = new JobOfferEvent(user, jobOffer);
		return this.jobOfferEventDao.save(jobOfferEvent);
	}

	@Override
	protected AbstractDao<JobOfferEvent> getDao() {
		return this.jobOfferEventDao;
	}

	@Override
	public JobOfferEvent save(final JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent, "Job offer event can't be null");
		logger.debug("Save jobOffer event {}", jobOfferEvent);

		final JobOffer jobOffer = jobOfferEvent.getJobOffer();
		this.jobOfferService.save(jobOffer);
		return this.jobOfferEventDao.save(jobOfferEvent);
	}

	public void setJobOfferEventDao(final JobOfferEventDao jobOfferEventDao) {
		Objects.requireNonNull(jobOfferEventDao);
		this.jobOfferEventDao = jobOfferEventDao;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

	public void setSecurityContext(final GedSecurityContext gedSecurityContext) {
		Objects.requireNonNull(gedSecurityContext);
		this.gedSecurityContext = gedSecurityContext;
	}
}
