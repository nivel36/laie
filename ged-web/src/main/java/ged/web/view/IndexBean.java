package ged.web.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import ged.ejb.candidate.Candidate;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class IndexBean extends AbstractBean {

	private static final long serialVersionUID = 3050470966176013477L;

	private List<Candidate> candidates;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobService;

	public void setJobService(JobOfferService jobService) {
		this.jobService = jobService;
	}

	private transient ScheduleModel schedule;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public Date getInitialDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public ScheduleModel getSchedule() {
		return this.schedule;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionBean.getUser();
		this.jobOffers = this.jobService.findLastJobOffers(user);
		this.candidates = new ArrayList<>();
		this.schedule = new LazyScheduleModel();
	}
}
