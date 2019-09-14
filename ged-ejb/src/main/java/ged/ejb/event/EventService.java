package ged.ejb.event;

import java.time.LocalDateTime;
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
public class EventService extends AbstractService<Event> {

	@Inject
	@Repository
	private EventDao eventDao;

	@Inject
	private JobCandidatureService jobCandidatureService;

	@Inject
	private SecurityContext securityContext;

	public void createEvent(final JobCandidature jobCandidature) {
		final Event event = new Event();
		event.setDate(LocalDateTime.now());
		event.setStatus(jobCandidature.getJobCandidatureState());
		event.setUser(this.securityContext.getLoggedUser());
		event.setJobCandidature(jobCandidature);
		event.setType(EventType.OTHER);
		this.eventDao.save(event);
	}

	@Override
	protected AbstractDao<Event> getDao() {
		return this.eventDao;
	}

	@Override
	public Event save(final Event event) {
		Objects.requireNonNull(event);
		this.updateJobCandidatureState(event);
		return super.save(event);
	}

	private void updateJobCandidatureState(final Event event) {
		final JobCandidature jobCandidature = event.getJobCandidature();
		final JobCandidatureState jobCandidatureState = jobCandidature.getJobCandidatureState();
		final JobCandidatureState eventState = event.getStatus();
		if (jobCandidatureState.equals(eventState)) {
			jobCandidature.setJobCandidatureState(event.getStatus());
			this.jobCandidatureService.save(jobCandidature);
		}
	}
}
