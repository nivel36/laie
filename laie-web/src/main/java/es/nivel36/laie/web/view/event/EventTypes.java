package es.nivel36.laie.web.view.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import es.nivel36.laie.ejb.event.JobCandidatureEventType;

@Named
@ApplicationScoped
public class EventTypes implements Serializable {

	private static final long serialVersionUID = 3602467667467065479L;

	private final List<JobCandidatureEventType> listOfEventTypes = new ArrayList<>();

	public List<JobCandidatureEventType> getList() {
		return this.listOfEventTypes;
	}

	@PostConstruct
	public void init() {
		for (final JobCandidatureEventType jobCandidatureEventType : JobCandidatureEventType.values()) {
			this.listOfEventTypes.add(jobCandidatureEventType);
		}
	}
}
