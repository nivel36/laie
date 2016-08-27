package ged.web.view;

import java.util.ArrayList;
import java.util.List;

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

	@Inject
	private JobOfferService jobService;

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
