package es.nivel36.laie.ejb.event;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class JobCandidatureEventService {

	private static final Logger logger = LoggerFactory.getLogger(JobCandidatureEventService.class);

	@Inject
	@Repository
	private JobCandidatureEventDao jobCandidatureEventDao;

	public JobCandidatureEventDto addJobCandidatureEvent(final JobCandidatureEventDto jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent, "Job candidature event can't be null");
		logger.debug("Save job candidature event {}", jobCandidatureEvent);
		JobCandidatureEvent entity = new JobCandidatureEvent();
		new JobCandidatureEventMerger().merge(entity, jobCandidatureEvent);
		jobCandidatureEventDao.insert(entity);
		return new JobCandidatureEventMapper().map(entity);
	}

	public void setJobCandidatureEventDao(final JobCandidatureEventDao jobCandidatureEventDao) {
		Objects.requireNonNull(jobCandidatureEventDao);
		this.jobCandidatureEventDao = jobCandidatureEventDao;
	}
}
