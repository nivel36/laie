package es.nivel36.laie.ejb.event;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.security.GedSecurityContext;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCreatedEvent;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class JobCandidatureEventService extends AbstractIndexedService<JobCandidatureEvent> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private GedSecurityContext gedSecurityContext;

	@Inject
	@Repository
	private JobCandidatureEventDao jobCandidatureEventDao;

	@Inject
	private JobCandidatureService jobCandidatureService;

	@Override
	protected AbstractIndexedDao<JobCandidatureEvent> getDao() {
		return this.jobCandidatureEventDao;
	}

	private User loggedUser() {
		return this.gedSecurityContext.getLoggedUser();
	}

	public void onJobCandidatureCreated(@JobCandidatureCreatedEvent @Observes final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature, "Job candidature can't be null");
		logger.debug("Create event for job candidature {}", jobCandidature);

		final User user = this.loggedUser();
		final JobCandidatureEvent jobCandidatureEvent = new JobCandidatureEvent(user, jobCandidature);
		jobCandidature.addJobCandidatureEvent(jobCandidatureEvent);
		this.save(jobCandidatureEvent);
	}

	@Override
	public JobCandidatureEvent save(final JobCandidatureEvent jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent, "Job candidature event can't be null");
		logger.debug("Save job candidature event {}", jobCandidatureEvent);

		final JobCandidatureState newJobCandidatureState = jobCandidatureEvent.getState();
		final JobCandidature jobCandidature = jobCandidatureEvent.getJobCandidature();
		if (!jobCandidature.hasState(newJobCandidatureState)) {
			jobCandidature.setState(newJobCandidatureState);
			this.jobCandidatureService.save(jobCandidature);
		}
		return super.save(jobCandidatureEvent);
	}
}
