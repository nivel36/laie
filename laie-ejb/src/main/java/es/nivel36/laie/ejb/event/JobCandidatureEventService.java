package es.nivel36.laie.ejb.event;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.security.GedSecurityContext;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCreatedEvent;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class JobCandidatureEventService {

	private static final Logger logger = LoggerFactory.getLogger(JobCandidatureEventService.class);

	@Inject
	private GedSecurityContext gedSecurityContext;

	@Inject
	@Repository
	private JobCandidatureEventDao jobCandidatureEventDao;

	private User loggedUser() {
		return this.gedSecurityContext.getLoggedUser();
	}

	public void onJobCandidatureCreated(@JobCandidatureCreatedEvent @Observes final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature, "Job candidature can't be null");
		logger.debug("Create event for job candidature {}", jobCandidature);
		final User user = this.loggedUser();
		final JobCandidatureEvent jobCandidatureEvent = new JobCandidatureEvent(user, jobCandidature);
		jobCandidature.addJobCandidatureEvent(jobCandidatureEvent);
		this.insert(jobCandidatureEvent);
	}

	public void insert(final JobCandidatureEvent jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent, "Job candidature event can't be null");
		logger.debug("Save job candidature event {}", jobCandidatureEvent);
		jobCandidatureEventDao.insert(jobCandidatureEvent);
	}
}
