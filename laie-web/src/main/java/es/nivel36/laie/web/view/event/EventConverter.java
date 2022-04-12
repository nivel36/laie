package es.nivel36.laie.web.view.event;

import java.util.Objects;

import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.web.core.AbstractConverter;

@FacesConverter(managed = true, forClass = JobCandidatureEvent.class)
public class EventConverter extends AbstractConverter<JobCandidatureEvent>{

	private JobCandidatureEventService jobCandidatureEventService;
	
	@Override
	protected JobCandidatureEvent getAsObject(Long id) {
		return null;
	}

	public void setJobCandidatureEventService(final JobCandidatureEventService jobCandidatureEventService) {
		Objects.requireNonNull(jobCandidatureEventService);
		this.jobCandidatureEventService = jobCandidatureEventService;
	}
}
