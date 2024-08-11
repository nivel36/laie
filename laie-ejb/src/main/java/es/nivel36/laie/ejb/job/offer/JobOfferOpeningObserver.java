package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.event.JobOfferCreatedEvent;
import jakarta.ejb.Schedule;
import jakarta.ejb.Startup;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * This singleton class is responsible for automatically opening job offers. The
 * opening process is scheduled to run daily at midnight, and it also handles
 * newly created job offers to determine if they should be opened immediately.
 */
@Singleton
@Startup
public class JobOfferOpeningObserver {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferOpeningObserver.class);

	private @Inject JobOfferService jobOfferService;

	/**
	 * Schedules a task to run every day at midnight to open job offers that are
	 * ready to be opened.
	 */
	@Schedule(hour = "0", minute = "0", second = "0", persistent = false)
	public void scheduleOpeningOfJobOffers() {
		logger.debug("Starting the job offer opening process");
		final List<JobOffer> jobOffers = jobOfferService.findJobOffersToOpen();
		for (final JobOffer offer : jobOffers) {
			openJobOffer(offer);
		}
	}

	private void openJobOffer(final JobOffer offer) {
		jobOfferService.changeState(offer, JobOfferState.OPENED, null, null);
	}

	/**
	 * Handles newly created job offers to check if they should be opened
	 * immediately based on their open date.
	 * 
	 * @param jobOffer with the <tt>JobOffer</tt> that has been created.
	 */
	public void handleCreatedJobOffer(final @ObservesAsync @JobOfferCreatedEvent JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer, "JobOffer cannot be null");
		logger.debug("Handling the creation of the new job offer {}", jobOffer);
		if (hasOpenDateArrived(jobOffer)) {
			openJobOffer(jobOffer);
		}
	}

	private boolean hasOpenDateArrived(final JobOffer jobOffer) {
		final LocalDate dateOpened = jobOffer.getOpenDate();
		final LocalDate now = LocalDate.now();
		return !now.isBefore(dateOpened);
	}
}
