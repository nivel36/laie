package ged.web.view;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class IndexView extends AbstractView {

	private static final long serialVersionUID = 3050470966176013477L;

	private List<Candidate> candidates;

	private List<JobOffer> jobOffers;
	
	@Inject
	private transient JobOfferService jobService;
	
	@Inject
	private transient CandidateService candidateService;

	private transient ScheduleModel schedule;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public LocalDate getInitialDate() {
		return LocalDate.now();
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public ScheduleModel getSchedule() {
		return this.schedule;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionUser.get();
		this.jobOffers = this.jobService.findLastJobOffers(user);
		this.candidates = this.candidateService.search(null, new Page(0,10)).getResultData();
		this.schedule = new LazyScheduleModel();
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}
}
