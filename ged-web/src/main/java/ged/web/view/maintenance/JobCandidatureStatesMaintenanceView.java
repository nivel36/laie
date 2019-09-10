package ged.web.view.maintenance;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class JobCandidatureStatesMaintenanceView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private List<JobCandidatureState> states;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	public List<JobCandidatureState> getStates() {
		return this.states;
	}

	@PostConstruct
	public void init() {
		logger.trace("Job candidature states maintenances init");
		this.states = this.jobCandidatureService.findJobCandidatureStates();
	}

	public void setJobOfferService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

}
