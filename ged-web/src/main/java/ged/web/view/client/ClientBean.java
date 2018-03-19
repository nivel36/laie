package ged.web.view.client;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CLIENT;
import static ged.web.core.util.Page.CLIENT_SEARCH;
import static ged.web.core.util.Page.JOB_OFFER;
import static ged.web.core.util.Page.JOB_OFFER_EDIT;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.Address;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean {

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	private Long clientId;

	@Inject
	private transient ClientService clientService;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private JobOffer selectedJobOffer;

	public void error() {
		to(CLIENT_SEARCH).doPost();
	}

	public void export() {
		// TODO
	}

	public Client getClient() {
		return this.client;
	}

	public Long getClientId() {
		return this.clientId;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public JobOffer getSelectedJobOffer() {
		return this.selectedJobOffer;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		this.client = this.clientService.find(this.clientId);
		if (this.client == null) {
			this.error();
		}
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		this.jobOffers = this.jobOfferService.findAllJobOffersByClient(this.client);
		if (this.client.isDeleted()) {
			Message.addWarning("message.erased_entity", "message.erased_entity");
		}
	}

	public String newJobOffer() {
		final JobOffer jobOffer = new JobOffer();
		jobOffer.setClient(this.client);
		final String url = to(CLIENT).withParams(map("id", this.clientId)).toUrl();
		this.flash.put("returnPage", url);
		this.flash.put("jobOffer", jobOffer);
		return to(JOB_OFFER_EDIT).toUrl();
	}

	public void onJobOfferSelect() {
		to(JOB_OFFER).withParams(map("id", this.selectedJobOffer.getId())).doGet();
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setSelectedJobOffer(final JobOffer selectedJobOffer) {
		this.selectedJobOffer = selectedJobOffer;
	}
}