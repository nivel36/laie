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
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.ejb.user.User;

@Stateless
public class JobCandidatureEventService extends AbstractService<JobCandidatureEvent> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private JobCandidatureEventDao jobCandidatureEventDao;

	@Inject
	private JobCandidatureService jobCandidatureService;
	
	@Inject
	private GedSecurityContext gedSecurityContext;

	public JobCandidatureEvent createEvent(final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature, "Job candidature can't be null");
		logger.debug("Create event for job candidature {}", jobCandidature);

		final User user = this.gedSecurityContext.getLoggedUser();
		final JobCandidatureEvent jobCandidatureEvent = new JobCandidatureEvent(user, jobCandidature);
		return this.jobCandidatureEventDao.save(jobCandidatureEvent);
	}

	@Override
	protected AbstractDao<JobCandidatureEvent> getDao() {
		return this.jobCandidatureEventDao;
	}

	@Override
	public JobCandidatureEvent save(final JobCandidatureEvent jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent, "Job candidature event can't be null");
		logger.debug("Save job candidature event {}", jobCandidatureEvent);

		this.updateJobCandidatureState(jobCandidatureEvent);
		return super.save(jobCandidatureEvent);
	}

	private void updateJobCandidatureState(final JobCandidatureEvent jobCandidatureEvent) {
		final JobCandidature jobCandidature = jobCandidatureEvent.getJobCandidature();
		final JobCandidatureState eventState = jobCandidatureEvent.getState();
		jobCandidatureService.updateState(jobCandidature, eventState);
	}
}
