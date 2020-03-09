package ged.web.view.event;

import java.util.Objects;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.event.JobCandidatureEvent;
import ged.ejb.event.JobCandidatureEventService;
import ged.web.core.view.AbstractLazyDataModel;

public class EventLazyDataModel extends AbstractLazyDataModel<JobCandidatureEvent> {

	private static final long serialVersionUID = 1L;

	private transient JobCandidatureEventService jobCandidatureEventService;

	public EventLazyDataModel(final JobCandidatureEventService jobCandidatureEventService) {
		Objects.requireNonNull(jobCandidatureEventService, "JobCandidatureEventService can't be null");
		this.jobCandidatureEventService = jobCandidatureEventService;
	}

	@Override
	protected AbstractIndexedService<JobCandidatureEvent> getService() {
		return this.jobCandidatureEventService;
	}
}
