package ged.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class IndexBean extends AbstractPageBean {

	private static final long serialVersionUID = 3050470966176013477L;

	private List<Candidate> candidates;

	private List<JobOffer> jobOffers;

	private transient final JobOfferService jobService;

	@Inject
	public IndexBean(final JobOfferService jobService) {
		Objects.requireNonNull(jobService);
		this.jobService = jobService;
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionBean.getUser();
		this.jobOffers = this.jobService.findLastJobOffers(user);
		this.candidates = new ArrayList<>();
	}
}
