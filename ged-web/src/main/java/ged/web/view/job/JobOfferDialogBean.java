package ged.web.view.job;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.JOB_OFFER;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.CloseDialogListener;
import ged.web.core.GedPermissionException;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferDialogBean extends AbstractDialogBean implements CloseDialogListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	private Client client;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobOfferService;

	private JobOffer buildNewJobOffer() {
		final JobOffer newJobOffer = new JobOffer();
		final Client client = this.getClientFromAttributes();
		newJobOffer.setClient(client);
		final User user = this.sessionBean.getUser();
		newJobOffer.setOwner(user);
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
		final Client clientFromAttributes = this.getAttribute("client");
		if (clientFromAttributes == null) {
			logger.error("Null client", this.client);
			throw new IllegalStateException("Null client");
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

	public boolean isUserHasPermissionToEditJobOffer() {
		return this.userHasPermissionToEdit(this.jobOffer);
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
		if (!this.isUserHasPermissionToEditJobOffer()) {
			throw new GedPermissionException();
		}
		this.jobOffer = this.jobOfferService.update(this.jobOffer);
		this.callback.onCloseDialog(this.jobOffer);
		Ajax.update("jobOfferForm", this.updateField);
		this.closeDialog();
	}
}