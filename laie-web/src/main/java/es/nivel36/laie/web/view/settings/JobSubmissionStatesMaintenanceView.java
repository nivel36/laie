package es.nivel36.laie.web.view.settings;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.submission.JobSubmissionState;
import es.nivel36.laie.ejb.job.submission.JobSubmissionStateService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class JobSubmissionStatesMaintenanceView extends AbstractView {

	private static final long serialVersionUID = 338713898830435971L;

	private static final Logger logger = LoggerFactory.getLogger(JobSubmissionStatesMaintenanceView.class);

	@Inject
	private transient JobSubmissionStateService jobSubmissionStateService;

	private List<JobSubmissionState> states;

	public List<JobSubmissionState> getStates() {
		return this.states;
	}

	@PostConstruct
	public void init() {
		logger.trace("Job jobSubmission states maintenances init");
		this.states = this.jobSubmissionStateService.findAll();
	}

	public void setJobSubmissionStateService(final JobSubmissionStateService jobSubmissionStateService) {
		this.jobSubmissionStateService = jobSubmissionStateService;
	}

}
