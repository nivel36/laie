package ged.web.view.event;

import javax.faces.convert.FacesConverter;

import ged.ejb.core.AbstractService;
import ged.ejb.event.JobCandidatureEvent;
import ged.ejb.event.JobCandidatureEventService;
import ged.web.core.view.AbstractConverter;

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