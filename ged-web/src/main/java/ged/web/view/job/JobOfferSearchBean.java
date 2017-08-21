package ged.web.view.job;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractPageBean {

	protected static final transient Logger logger = LoggerFactory.getLogger(JobOfferSearchBean.class.getName());

	private static final long serialVersionUID = 8777365288968792501L;

	private String clientName;

	private final transient JobOfferService jobOfferService;

	private String name;

	private List<JobOffer> jobOffers;

	@Inject
	public JobOfferSearchBean(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

	public boolean canEdit(final JobOffer jobOffer) {
		final User owner = jobOffer.getOwner();
		final User user = this.sessionBean.getUser();
		if (owner.equals(user)) {
			return true;
		}
		if (user.hasRole("ADMIN")) {
			return true;
		}
		if (user.hasRole("RECRUITER_ADMIN")) {
			return true;
		}
		return false;
	}

	public void clean() {
		cleanSearchFields();
		search();
	}

	private void cleanSearchFields() {
		this.name = null;
		this.clientName = null;
	}

	public String edit(final JobOffer jobOffer) {
		this.flash.put("jobOffer", jobOffer);
		return "jobOfferEdit?faces-redirect=true";
	}

	public String getClientName() {
		return this.clientName;
	}

	public String getName() {
		return this.name;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	@PostConstruct
	public void init() {
		search();
	}

	public String newJobOffer() {
		return "jobOfferEdit?faces-redirect=true";
	}

	public void remove(final JobOffer jobOffer) {
		this.jobOfferService.delete(jobOffer);
		search();
	}

	public void search() {
		logger.debug("Searching for JobOffers");
		jobOffers = this.jobOfferService.searchByNameAndClient(this.name, this.clientName, null);
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
