package es.nivel36.laie.web.view.event;

import java.util.Objects;

import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

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
