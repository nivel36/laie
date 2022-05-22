package es.nivel36.laie.web.view.event;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final long serialVersionUID = 1204346979477586986L;
	
	private JobCandidatureEvent event;
	
	@PostConstruct
	public void init() {
	}

	public JobCandidatureEvent getEvent() {
		return event;
	}

	public void setEvent(JobCandidatureEvent event) {
		this.event = event;
	}
	
	public void save() {
	}
}
