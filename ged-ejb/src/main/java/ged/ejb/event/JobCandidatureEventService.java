package ged.ejb.event;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.SecurityContext;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.candidature.JobCandidatureState;

@Stateless
public class JobCandidatureEventService extends AbstractService<JobCandidatureEvent> {

	@Inject
	@Repository
	private JobCandidatureEventDao jobCandidatureEventDao;

	@Inject
	private JobCandidatureService jobCandidatureService;

	@Inject
	private SecurityContext securityContext;

	public void createEvent(final JobCandidature jobCandidature) {
		final JobCandidatureEvent jobCandidatureEvent = new JobCandidatureEvent(this.securityContext.getLoggedUser(), jobCandidature);
		this.jobCandidatureEventDao.save(jobCandidatureEvent);
	}

	@Override
	protected AbstractDao<JobCandidatureEvent> getDao() {
		return this.jobCandidatureEventDao;
	}

	@Override
	public JobCandidatureEvent save(final JobCandidatureEvent jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent);
		this.updateJobCandidatureState(jobCandidatureEvent);
		return super.save(jobCandidatureEvent);
	}

	private void updateJobCandidatureState(final JobCandidatureEvent jobCandidatureEvent) {
		final JobCandidature jobCandidature = jobCandidatureEvent.getJobCandidature();
		final JobCandidatureState jobCandidatureState = jobCandidature.getJobCandidatureState();
		final JobCandidatureState eventState = jobCandidatureEvent.getStatus();
		if (!jobCandidatureState.equals(eventState)) {
			jobCandidature.setJobCandidatureState(jobCandidatureEvent.getStatus());
			this.jobCandidatureService.save(jobCandidature);
		}
	}
}
