package es.nivel36.laie.web.view.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureEvent;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureEventType;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddEventView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(AddEventView.class);

	private static final long serialVersionUID = 1204346979477586986L;

	private JobCandidatureEvent event;

	private @Param JobCandidature jobCandidature;

	private transient @Inject JobCandidatureStateService jobCandidatureStateService;

	private List<SelectItem> states;

	private List<JobCandidatureEventType> types;
	
	@PostConstruct
	public void init() {
		logger.debug("AddEventView init");
		if (jobCandidature == null) {
			throw new IllegalPageStateException();
		}
		types = Arrays.asList(JobCandidatureEventType.values());
		initComboStates();
	}

	private void initComboStates() {
		states = new ArrayList<>();
		JobCandidatureState init = this.jobCandidatureStateService.findInitialState();
		for (JobCandidatureState nextState : init.getNextStates()) {
			states.add(new SelectItem(nextState.getName(),
					this.translator.message("job_offer_state." + nextState.getName())));
		}
	}

	public void save() {
	}


	public JobCandidatureEvent getEvent() {
		return event;
	}

	public JobCandidature getJobCandidature() {
		return jobCandidature;
	}

	public List<SelectItem> getStates() {
		return states;
	}

	public List<JobCandidatureEventType> getTypes() {
		return types;
	}

	public void setEvent(JobCandidatureEvent event) {
		this.event = event;
	}

	public void setJobCandidatureStateService(JobCandidatureStateService jobCandidatureStateService) {
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}
