package ged.web.view.event;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Page;
import ged.ejb.event.Event;
import ged.ejb.event.EventService;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.user.User;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private Event event;

	@Inject
	private transient EventService eventService;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	public Event getEvent() {
		return this.event;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionUser.get();
		this.event = new Event();
		this.event.setUser(user);
		this.jobCandidatures = this.jobCandidatureService.findJobCandidatures(user, Page.ALL);
	}

	public String save() {
		this.eventService.save(this.event);
		return PageEnum.EVENT_SEARCH.getRedirectedUrl();
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}
}
