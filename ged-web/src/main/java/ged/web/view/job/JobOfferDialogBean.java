package ged.web.view.job;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.JOB_OFFER;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferDialogBean extends AbstractDialogBean {

	private static final long serialVersionUID = -4373329969104383876L;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobOfferService;

	@Override
	protected void dispose() {
		this.jobOffer = null;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@Override
	protected void init() {
		this.jobOffer = new JobOffer();
		final Client client = (Client) getAttribute("client");
		if (client == null) {
			throw new IllegalStateException("Client can not be null");
		}
		this.jobOffer.setClient(client);
		this.jobOffer.setOwner(this.sessionBean.getUser());
	}

	public void save() {
		this.jobOfferService.insert(this.jobOffer);
		to(JOB_OFFER).withParams(map("id", this.jobOffer.getId())).doGet();
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
