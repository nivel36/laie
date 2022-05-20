package es.nivel36.laie.ejb.event;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.login.GedSecurityContext;

@Stateless
public class JobOfferEventService  {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferEventService.class);

	@Inject
	private GedSecurityContext gedSecurityContext;
	
	@Inject
	private JobOfferEventDao jobOfferEventDao;

	public JobOfferEvent createEvent(final JobOffer jobOffer, final JobOfferState jobOfferState, final String notes) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(jobOfferState);
		logger.debug("Create event for job offer {}", jobOffer);
		final User user = this.gedSecurityContext.getLoggedUser();
		final JobOfferEvent jobOfferEvent = new JobOfferEvent();
		jobOfferEvent.setDate(LocalDateTime.now());
		jobOfferEvent.setJobOffer(jobOffer);
		jobOfferEvent.setState(jobOfferState);
		jobOfferEvent.setNotes(notes);
		jobOfferEvent.setUser(user);
		this.jobOfferEventDao.insert(jobOfferEvent);
		return jobOfferEvent;
	}
	
	public void updateNotes(final String jobOfferEventId, String notes) {
		Objects.requireNonNull(jobOfferEventId);
		final JobOfferEvent jobOfferEvent = this.jobOfferEventDao.findById(jobOfferEventId);
		jobOfferEvent.setNotes(notes);
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
