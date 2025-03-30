package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.submission.JobSubmission;
import es.nivel36.laie.ejb.job.submission.JobSubmissionService;
import es.nivel36.laie.ejb.job.submission.event.JobSubmissionCompletedEvent;
import jakarta.ejb.Schedule;
import jakarta.ejb.Startup;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * This singleton class is responsible for automatically closing job offers. The
 * closing process is scheduled to run daily at midnight and also triggers when
 * a job submission or a job offer completion event occurs to check if the job offer
 * should be closed.
 */
@Singleton
@Startup
public class JobOfferClosingObserver {

    private static final Logger logger = LoggerFactory.getLogger(JobOfferClosingObserver.class);

    private @Inject JobOfferService jobOfferService;
    private @Inject JobSubmissionService jobSubmissionService;

    /**
     * Schedules a task to run every day at midnight to close job offers that meet
     * the criteria for closure.
     */
    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    public void scheduleClosingOfJobOffers() {
        logger.debug("Starting the job offer closing process");
        final List<JobOffer> jobOffers = jobOfferService.findJobOffersToClose();
        for (final JobOffer offer : jobOffers) {
            this.closeJobOffer(offer);
        }
    }
    
    private void closeJobOffer(final JobOffer offer) {
        offer.setCompletionDate(LocalDate.now());
        this.jobOfferService.changeState(offer, JobOfferState.CLOSED, null, null);
    }

    /**
     * Handles the completion of a job submission event and checks if the associated
     * job offer should be closed based on the number of approved submissions.
     * 
     * @param jobSubmission the <tt>JobSubmission</tt> that has been completed. Cannot be null.
     * @throws NullPointerException if jobSubmission is null.
     */
    public void handleJobSubmissionCompleted(@Observes @JobSubmissionCompletedEvent final JobSubmission jobSubmission) {
        Objects.requireNonNull(jobSubmission, "JobSubmission cannot be null");
        logger.debug("Handling the completion of the job submission {}", jobSubmission);
        final JobOffer jobOffer = jobSubmission.getJobOffer();
        if (this.isCompleted(jobOffer)) {
            this.finishJobOffer(jobOffer);
        }
    }
    
    private void finishJobOffer(final JobOffer offer) {
        offer.setCompletionDate(LocalDate.now());
        this.jobOfferService.changeState(offer, JobOfferState.FINISHED, null, null);
    }

    private boolean isCompleted(final JobOffer jobOffer) {
        int availablepositions = jobOffer.getPositions() - 1; // we are adding a new completed job offer
        long approvedSubmissionsCount = jobSubmissionService.countApprovedJobSubmissions(jobOffer);
        return availablepositions == approvedSubmissionsCount;
    }

    /**
     * Sets the <tt>JobOfferService</tt>. This method should be used for setting or
     * changing the <tt>JobOfferService</tt> instance, primarily in testing
     * scenarios.
     * 
     * @param jobOfferService the <tt>JobOfferService</tt> to be set. Cannot be null.
     * @throws NullPointerException if jobOfferService is null.
     */
    public void setJobOfferService(final JobOfferService jobOfferService) {
        Objects.requireNonNull(jobOfferService, "JobOfferService cannot be null");
        this.jobOfferService = jobOfferService;
    }

    /**
     * Sets the <tt>JobSubmissionService</tt>. This method should be used for
     * setting or changing the <tt>JobSubmissionService</tt> instance, primarily in
     * testing scenarios.
     * 
     * @param jobSubmissionService the <tt>JobSubmissionService</tt> to be set. Cannot be null.
     * @throws NullPointerException if jobSubmissionService is null.
     */
    public void setJobSubmissionService(final JobSubmissionService jobSubmissionService) {
        Objects.requireNonNull(jobSubmissionService, "JobSubmissionService cannot be null");
        this.jobSubmissionService = jobSubmissionService;
    }
}
