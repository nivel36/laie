package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.job.offer.JobOfferProcess;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class JobProcesses {

	private transient @Inject JobOfferService jobOfferService;
	private List<JobOfferProcess> listOfJobOfferProcess;

	@PostConstruct
	public void init() {
		this.listOfJobOfferProcess = this.jobOfferService.findJobOfferProcess();
	}

	public List<JobOfferProcess> getList() {
		return this.listOfJobOfferProcess;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}