package ged.web.view.client;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.util.MessageUtils;
import ged.web.core.util.NavigationUtils;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class ClientViewBean extends AbstractPageBean {

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public void setJobOfferService(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	private String id;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;
	
	private boolean editable;

	public void editClient() {
		editable=true;
	}

	public void export() {

	}
	
	public Client getClient() {
		return this.client;
	}

	public String getId() {
		return this.id;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.id == null) {
			NavigationUtils.gotoPage("clientSearch");
		}
		long clientId = 0;
		try {
			clientId = Long.parseLong(this.id);
		} catch (final NumberFormatException ex) {
			NavigationUtils.gotoPage("clientSearch");
		}
		this.client = this.clientService.find(clientId);
		if (this.client == null) {
			NavigationUtils.gotoPage("clientSearch");
		}

		this.jobOffers = this.jobOfferService.findAllJobOffersByClient(this.client);
		if (this.client.isDeleted()) {
			MessageUtils.addWarningMessage("message.erased_entity", "message.erased_entity");
		}
	}

	public boolean isEditable() {
		return editable;
	}

	public String modifyClient() {
		this.flash.put("client", this.client);
		return "clientEdit?faces-redirect=true";
	}

	public void saveClient() {
		editable=false;
		clientService.save(client);
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void undeleteClient() {
		this.clientService.undelete(this.client);
	}
}