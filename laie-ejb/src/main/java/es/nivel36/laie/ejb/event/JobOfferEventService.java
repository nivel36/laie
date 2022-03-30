package es.nivel36.laie.ejb.event;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Repository;
import es.nivel36.laie.ejb.core.security.GedSecurityContext;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferDao;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class JobOfferEventService  {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferEventService.class);

	@Inject
	private GedSecurityContext gedSecurityContext;
	
	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	@Inject
	@Repository
	private JobOfferEventDao jobOfferEventDao;

	public void createEvent(final String jobOfferUid, final JobOfferState jobOfferState, final String notes) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(jobOfferState);
		logger.debug("Create event for job offer {}", jobOfferUid);
		final User user = this.gedSecurityContext.getLoggedUser();
		final JobOffer jobOffer = this.jobOfferDao.findByUid(jobOfferUid);
		final JobOfferEvent jobOfferEvent = new JobOfferEvent();
		jobOfferEvent.setDate(LocalDateTime.now());
		jobOfferEvent.setJobOffer(jobOffer);
		jobOfferEvent.setState(jobOfferState);
		jobOfferEvent.setNotes(notes);
		jobOfferEvent.setUser(user);
		this.jobOfferEventDao.insert(jobOfferEvent);
	}
	
	public void updateNotes(final String jobOfferEventUid, String notes) {
		Objects.requireNonNull(jobOfferEventUid);
		final JobOfferEvent jobOfferEvent = this.jobOfferEventDao.findByUid(jobOfferEventUid);
		jobOfferEvent.setNotes(notes);
	}
	
	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		Objects.requireNonNull(jobOfferDao);
		this.jobOfferDao = jobOfferDao;
	}

	public void setJobOfferEventDao(final JobOfferEventDao jobOfferEventDao) {
		Objects.requireNonNull(jobOfferEventDao);
		this.jobOfferEventDao = jobOfferEventDao;
	}

	public void setSecurityContext(final GedSecurityContext gedSecurityContext) {
		Objects.requireNonNull(gedSecurityContext);
		this.gedSecurityContext = gedSecurityContext;
	}
}
