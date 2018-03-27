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
import ged.web.core.CallbackListener;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferDialogBean extends AbstractDialogBean implements CallbackListener {

	private static final long serialVersionUID = -4373329969104383876L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobOfferService;

	private JobOffer buildNewJobOffer() {
		final JobOffer newJobOffer = new JobOffer();
		newJobOffer.setClient(this.getClientFromAttributes());
		newJobOffer.setOwner(this.sessionBean.getUser());
		return newJobOffer;
	}

	public void cleanClient() {
		this.client = new Client();
	}

	@Override
	protected void dispose() {
		this.jobOffer = null;
	}

	@Override
	public void doAction(final Object value) {
		this.client = (Client) value;
	}

	public Client getClient() {
		return this.client;
	}

	private Client getClientFromAttributes() {
		final Long clientId = this.getAttribute("client_id");
		final Client client;
		if (clientId != null) {
			client = this.clientService.find(clientId);
		}
		else {
			client = null;
		}
		return client;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@Override
	protected void init() {
		JobOffer newJobOffer = this.getAttribute("jobOffer");
		if (newJobOffer == null) {
			newJobOffer = this.buildNewJobOffer();
		}
		this.jobOffer = newJobOffer;
		this.client = newJobOffer.getClient();
	}

	private void insertJobOffer() {
		this.jobOfferService.insert(this.jobOffer);
		to(JOB_OFFER).withParams(map("jobOfferId", this.jobOffer.getId())).doGet();
	}

	public void save() {
		this.jobOffer.setClient(this.client);
		if (this.jobOffer.getId() == 0) {
			this.insertJobOffer();
		}
		else {
			this.updateJobOffer();
		}
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	private void updateJobOffer() {
		this.jobOffer = this.jobOfferService.update(this.jobOffer);
		to(JOB_OFFER).withParams(map("jobOfferId", this.jobOffer.getId())).doGet();
	}
}