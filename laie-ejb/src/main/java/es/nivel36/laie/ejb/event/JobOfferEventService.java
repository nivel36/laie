package es.nivel36.laie.ejb.event;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.security.GedSecurityContext;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class JobOfferEventService  {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferEventService.class);

	@Inject
	private GedSecurityContext gedSecurityContext;

	@Inject
	@Repository
	private JobOfferEventDao jobOfferEventDao;

	public void createEvent(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Create event for job offer {}", jobOffer);

		final User user = this.gedSecurityContext.getLoggedUser();
		final JobOfferEvent jobOfferEvent = new JobOfferEvent(user, jobOffer);
		this.jobOfferEventDao.insert(jobOfferEvent);
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
