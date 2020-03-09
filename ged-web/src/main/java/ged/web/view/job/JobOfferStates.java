package ged.web.view.job;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.offer.JobOfferService;
import ged.ejb.job.offer.JobOfferState;

@Named
@ApplicationScoped
public class JobOfferStates implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<JobOfferState> states;

	public List<JobOfferState> getList() {
		return this.states;
	}

	@PostConstruct
	public void init() {
		this.states = this.jobOfferService.findJobOfferStates();
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
