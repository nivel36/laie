package es.nivel36.laie.web.view.settings;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class JobOfferStatesMaintenanceView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<JobOfferState> states;

	public List<JobOfferState> getStates() {
		return this.states;
	}

	@PostConstruct
	public void init() {
		logger.trace("Job offer states maintenances init");
		this.states = this.jobOfferService.findJobOfferStates();
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

}