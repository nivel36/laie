package ged.web.view.job;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractPageBean {

	protected static final transient Logger logger = Logger.getLogger(JobOfferSearchBean.class.getName());

	private static final long serialVersionUID = 8777365288968792501L;

	private String clientName;

	private final transient JobOfferService jobOfferService;

	private String name;

	private Paginator<JobOffer> paginator;

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

	public Paginator<JobOffer> getPaginator() {
		return this.paginator;
	}

	@PostConstruct
	public void init() {
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
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
		logger.fine("Searching for JobOffers");
		final List<JobOffer> jobOffers = this.jobOfferService.searchByNameAndClient(this.name, this.clientName, null);
		this.paginator.setEntities(jobOffers);
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
