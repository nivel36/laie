package es.nivel36.laie.web.view.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.submission.JobSubmission;
import es.nivel36.laie.ejb.job.submission.JobSubmissionEvent;
import es.nivel36.laie.ejb.job.submission.JobSubmissionEventType;
import es.nivel36.laie.ejb.job.submission.JobSubmissionService;
import es.nivel36.laie.ejb.job.submission.JobSubmissionState;
import es.nivel36.laie.ejb.job.submission.JobSubmissionStateService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.job.ViewJobView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(AddEventView.class);

	private static final long serialVersionUID = 1204346979477586986L;

	private JobSubmissionEvent event;

	private @Param JobSubmission jobSubmission;

	private transient @Inject JobSubmissionStateService jobSubmissionStateService;

	private transient @Inject JobSubmissionService jobSubmissionService;

	private List<JobSubmissionState> states;

	private List<JobSubmissionEventType> types;

	@PostConstruct
	public void init() {
		logger.debug("AddEventView init");
		if (jobSubmission == null) {
			throw new IllegalPageStateException();
		}
		event = new JobSubmissionEvent();
		event.setJobSubmission(jobSubmission);
		event.setUser(this.sessionUser.get());
		event.setDate(LocalDateTime.now());
		types = Arrays.asList(JobSubmissionEventType.values());
		initComboStates();
	}

	private void initComboStates() {
		states = new ArrayList<>();
		JobSubmissionState currentState = jobSubmissionStateService.findByName(jobSubmission.getState().getName());
		states.add(currentState);
		states.addAll(jobSubmissionStateService.findNextStates(currentState));
	}

	public void save() {
		this.jobSubmissionService.addJobSubmissionEvent(event);
		Faces.redirect(ViewJobView.getUrl(jobSubmission.getJobOffer().getId()));
	}

	public JobSubmissionEvent getEvent() {
		return event;
	}

	public JobSubmission getJobSubmission() {
		return jobSubmission;
	}

	public List<JobSubmissionState> getStates() {
		return states;
	}

	public List<JobSubmissionEventType> getTypes() {
		return types;
	}

	public void setEvent(JobSubmissionEvent event) {
		this.event = event;
	}

	public void setJobSubmissionService(JobSubmissionService jobSubmissionService) {
		Objects.requireNonNull(jobSubmissionService);
		this.jobSubmissionService = jobSubmissionService;
	}

	public void setJobSubmissionStateService(JobSubmissionStateService jobSubmissionStateService) {
		Objects.requireNonNull(jobSubmissionStateService);
		this.jobSubmissionStateService = jobSubmissionStateService;
	}
}
