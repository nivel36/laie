package es.nivel36.laie.web.view.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureEvent;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureEventType;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.job.ViewJobView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(AddEventView.class);

	private static final long serialVersionUID = 1204346979477586986L;

	private JobCandidatureEvent event;

	private @Param JobCandidature jobCandidature;

	private transient @Inject JobCandidatureStateService jobCandidatureStateService;

	private transient @Inject JobCandidatureService jobCandidatureService;

	private List<JobCandidatureState> states;

	private List<JobCandidatureEventType> types;

	@PostConstruct
	public void init() {
		logger.debug("AddEventView init");
		if (jobCandidature == null) {
			throw new IllegalPageStateException();
		}
		event = new JobCandidatureEvent();
		event.setJobCandidature(jobCandidature);
		types = Arrays.asList(JobCandidatureEventType.values());
		initComboStates();
	}

	private void initComboStates() {
		states = new ArrayList<>();
		JobCandidatureState currentState = jobCandidatureStateService.findByName(jobCandidature.getState().getName());
		states.add(currentState);
		states.addAll(currentState.getNextStates());
	}

	public void save() {
		this.jobCandidatureService.addJobCandidatureEvent(event);
		Faces.redirect(ViewJobView.getUrl(jobCandidature.getJobOffer().getId()));
	}

	public JobCandidatureEvent getEvent() {
		return event;
	}

	public JobCandidature getJobCandidature() {
		return jobCandidature;
	}

	public List<JobCandidatureState> getStates() {
		return states;
	}

	public List<JobCandidatureEventType> getTypes() {
		return types;
	}

	public void setEvent(JobCandidatureEvent event) {
		this.event = event;
	}

	public void setJobCandidatureService(JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setJobCandidatureStateService(JobCandidatureStateService jobCandidatureStateService) {
		Objects.requireNonNull(jobCandidatureStateService);
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}
