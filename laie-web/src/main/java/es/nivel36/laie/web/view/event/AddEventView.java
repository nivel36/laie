package es.nivel36.laie.web.view.event;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.user.SimpleUser;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final long serialVersionUID = 8985623161372187380L;

	private JobCandidature jobCandidature;

	private JobCandidatureEvent jobCandidatureEvent;

	@Inject
	private transient JobCandidatureEventService jobCandidatureEventService;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	@PostConstruct
	public void init() {
		final String jobOfferId = this.getValueFromGetParameters("jobOfferId");
		final String clientId = this.getValueFromGetParameters("clientId");
		this.jobCandidature = this.jobCandidatureService.findJobCandidature(jobOfferId, clientId);
		final User user = this.sessionUser.get();
		this.jobCandidatureEvent = this.initEvent(user);
		this.jobCandidatures = this.jobCandidatureService.findUsersJobCandidatures(user.getId(),
				Page.TEN_RESULTS_PER_PAGE);
	}

	public JobCandidatureEvent initEvent(final User user) {
		final JobCandidatureEvent event = new JobCandidatureEvent();
		event.setUser(new SimpleUser(user));
		return event;
	}

	public String save() {
		this.jobCandidatureEventService.addJobCandidatureEvent(jobCandidatureEvent);
		return null;
	}
	
	public void updateState() {
		//this.jobCandidatureEvent.setState(this.jobCandidatureEvent.getJobCandidature().getState());
	}
	
	public JobCandidatureEvent getEvent() {
		return this.jobCandidatureEvent;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public void setEvent(final JobCandidatureEvent jobCandidatureEvent) {
		this.jobCandidatureEvent = jobCandidatureEvent;
	}

	public void setEventService(final JobCandidatureEventService jobCandidatureEventService) {
		Objects.requireNonNull(jobCandidatureEventService);
		this.jobCandidatureEventService = jobCandidatureEventService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}
}
