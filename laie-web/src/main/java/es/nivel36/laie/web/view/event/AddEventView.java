package es.nivel36.laie.web.view.event;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "jobCandidatureId")
	private JobCandidature jobCandidature;

	private JobCandidatureEvent jobCandidatureEvent;

	@Inject
	private transient JobCandidatureEventService jobCandidatureEventService;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	public JobCandidatureEvent getEvent() {
		return this.jobCandidatureEvent;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionUser.get();
		this.jobCandidatureEvent = this.initEvent(user);
		this.jobCandidatures = this.jobCandidatureService.findJobCandidatures(user, Page.ALL_RESULTS);
	}

	public JobCandidatureEvent initEvent(final User user) {
		return new JobCandidatureEvent(user, this.jobCandidature);
	}

	public String save() {
		this.jobCandidatureEventService.save(this.jobCandidatureEvent);
		return this.navigator.getRedirectUrl(PageEnum.JOB, this.jobCandidature.getJobOffer());
	}

	public void setEvent(final JobCandidatureEvent jobCandidatureEvent) {
		this.jobCandidatureEvent = jobCandidatureEvent;
	}

	public void setEventService(final JobCandidatureEventService jobCandidatureEventService) {
		this.jobCandidatureEventService = jobCandidatureEventService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void updateState() {
		this.jobCandidatureEvent.setState(this.jobCandidatureEvent.getJobCandidature().getState());
	}
}
