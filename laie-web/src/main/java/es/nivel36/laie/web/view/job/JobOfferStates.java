package es.nivel36.laie.web.view.job;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class JobOfferStates implements Serializable {

	private static final long serialVersionUID = 4806630306147691470L;

	@Inject
	private transient JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		this.states = this.jobOfferService.findJobOfferStates();
	}

	private List<JobOfferState> states;

	public List<JobOfferState> getList() {
		return this.states;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}
