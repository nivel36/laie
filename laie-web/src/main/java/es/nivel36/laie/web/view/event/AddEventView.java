package es.nivel36.laie.web.view.event;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.core.model.Page;
import es.nivel36.laie.ejb.event.JobCandidatureEventDto;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final long serialVersionUID = 8985623161372187380L;

	private JobCandidatureDto jobCandidature;

	private JobCandidatureEventDto jobCandidatureEvent;

	@Inject
	private transient JobCandidatureEventService jobCandidatureEventService;

	private List<JobCandidatureDto> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	@PostConstruct
	public void init() {
		final String jobOfferUid = this.getValueFromGetParameters("jobOfferUid");
		final String clientUid = this.getValueFromGetParameters("clientUid");
		this.jobCandidature = this.jobCandidatureService.findJobCandidature(jobOfferUid, clientUid);
		final UserDto user = this.sessionUser.get();
		this.jobCandidatureEvent = this.initEvent(user);
		this.jobCandidatures = this.jobCandidatureService.findUsersJobCandidatures(user.getUid(),
				Page.TEN_RESULTS_PER_PAGE);
	}

	public JobCandidatureEventDto initEvent(final UserDto user) {
		final JobCandidatureEventDto event = new JobCandidatureEventDto();
		event.setUser(new SimpleUserDto(user));
		return event;
	}

	public String save() {
		this.jobCandidatureEventService.addJobCandidatureEvent(jobCandidatureEvent);
		return null;
	}
	
	public void updateState() {
		//this.jobCandidatureEvent.setState(this.jobCandidatureEvent.getJobCandidature().getState());
	}
	
	public JobCandidatureEventDto getEvent() {
		return this.jobCandidatureEvent;
	}

	public List<JobCandidatureDto> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public void setEvent(final JobCandidatureEventDto jobCandidatureEvent) {
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
