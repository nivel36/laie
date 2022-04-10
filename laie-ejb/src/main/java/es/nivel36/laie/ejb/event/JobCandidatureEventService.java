package es.nivel36.laie.ejb.event;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class JobCandidatureEventService {

	@Inject
	@Repository
	private JobCandidatureEventDao jobCandidatureEventDao;

	public void setJobCandidatureEventDao(final JobCandidatureEventDao jobCandidatureEventDao) {
		Objects.requireNonNull(jobCandidatureEventDao);
		this.jobCandidatureEventDao = jobCandidatureEventDao;
	}
}
