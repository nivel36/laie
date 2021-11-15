package es.nivel36.laie.web.view.maintenance;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class JobCandidatureStatesMaintenanceView extends AbstractView {

	private static final long serialVersionUID = 338713898830435971L;

	private static final Logger logger = LoggerFactory.getLogger(JobCandidatureStatesMaintenanceView.class);

	@Inject
	private transient JobCandidatureStateService jobCandidatureStateService;

	private List<JobCandidatureState> states;

	public List<JobCandidatureState> getStates() {
		return this.states;
	}

	@PostConstruct
	public void init() {
		logger.trace("Job candidature states maintenances init");
		this.states = this.jobCandidatureStateService.findAll();
	}

	public void setJobCandidatureStateService(final JobCandidatureStateService jobCandidatureStateService) {
		this.jobCandidatureStateService = jobCandidatureStateService;
	}

}
