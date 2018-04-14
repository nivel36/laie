package ged.web.view.job;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.JOB_OFFER;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.CloseDialogListener;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferDialogBean extends AbstractDialogBean implements CloseDialogListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

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
		this.client = null;
	}

	public Client getClient() {
		return this.client;
	}

	private Client getClientFromAttributes() {
		final Long clientId = this.getAttribute("client_id");
		if (clientId == null) {
			return null;
		}
		final Client clientFromAttributes = this.clientService.find(clientId);
		if (clientFromAttributes == null) {
			logger.error("client with id {} not found", clientId);
			throw new IllegalStateException();
		}
		return clientFromAttributes;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@Override
	protected void init() {
		logger.debug("JobOfferDialogBean init");
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

	@Override
	public void onCloseDialog(final Object value) {
		this.client = (Client) value;
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