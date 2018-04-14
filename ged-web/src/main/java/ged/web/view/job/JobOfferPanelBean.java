package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferPanelBean extends AbstractBean {

	private static final long serialVersionUID = -8972909678807698224L;

	private Long clientId;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	public Long getClientId() {
		return this.clientId;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	@PostConstruct
	public void init() {
		final String clientIdValue = this.getValueFromGetParameters("clientId");
		if (clientIdValue != null) {
			this.clientId = Long.parseLong(clientIdValue);
			this.jobOffers = this.jobOfferService.findJobOffersByClientId(this.clientId);
		}
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}