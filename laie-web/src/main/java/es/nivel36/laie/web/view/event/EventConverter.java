package es.nivel36.laie.web.view.event;

import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.core.AbstractService;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = JobCandidatureEvent.class)
public class EventConverter extends AbstractConverter<JobCandidatureEvent> {

	private JobCandidatureEventService jobCandidatureEventService;

	@Override
	protected AbstractService<JobCandidatureEvent> getService() {
		return this.jobCandidatureEventService;
	}

	public void setEventService(final JobCandidatureEventService jobCandidatureEventService) {
		this.jobCandidatureEventService = jobCandidatureEventService;
	}
}