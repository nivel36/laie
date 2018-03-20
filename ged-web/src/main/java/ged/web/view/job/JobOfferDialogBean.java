package ged.web.view.job;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.JOB_OFFER;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractDialogBean;
import ged.web.view.client.ClientSelecteable;
import ged.web.view.client.SelectClientAction;

@Named
@ViewScoped
public class JobOfferDialogBean extends AbstractDialogBean implements ClientSelecteable {

	private static final long serialVersionUID = -4373329969104383876L;

	@Inject
	private transient ClientService clientService;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobOfferService;

	private transient final SelectClientAction selectClientCallback = new SelectClientAction(this);

	public void cleanClient() {
		this.jobOffer.setClient(new Client());
	}

	@Override
	protected void dispose() {
		this.jobOffer = null;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public SelectClientAction getSelectClientCallback() {
		return this.selectClientCallback;
	}

	@Override
	protected void init() {
		this.jobOffer = new JobOffer();
		this.setClientIntoJobOffer();
		this.jobOffer.setOwner(this.sessionBean.getUser());
	}

	@Override
	public void onClientSelect(final Client client) {
		this.jobOffer.setClient(client);
	}

	public void save() {
		this.jobOfferService.insert(this.jobOffer);
		to(JOB_OFFER).withParams(map("jobOfferId", this.jobOffer.getId())).doGet();
	}

	private void setClientIntoJobOffer() {
		final Long clientId = this.getAttribute("client");
		final Client client;
		if (clientId != null) {
			client = this.clientService.find(clientId);
		}
		else {
			client = null;
		}
		this.jobOffer.setClient(client);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
