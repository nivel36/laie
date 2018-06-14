package ged.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.GedPermissionException;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferDialogBean extends AbstractDialogBean {

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
		final User user = this.sessionBean.getUser();
		newJobOffer.setOwner(user);
		return newJobOffer;
	}

	public void cleanClient() {
		this.client = new Client();
	}

	public Client getClient() {
		return this.client;
	}

	private Client getClientFromAttributes() {
		final Long clientId = this.getIdFromParameters("clientId");
		if (clientId == null) {
			return null;
		}
		else {
			return this.clientService.find(clientId);
		}
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		logger.debug("JobOfferDialogBean init");
		final Long jobOfferId = this.getIdFromParameters("jobOfferId");
		if (jobOfferId == null) {
			this.jobOffer = this.buildNewJobOffer();
			final Client client = this.getClientFromAttributes();
			this.jobOffer.setClient(client);
		}
		else {
			this.jobOffer = this.jobOfferService.find(jobOfferId);
		}
		this.client = this.jobOffer.getClient();
	}

	private void insertJobOffer() {
		this.jobOfferService.insert(this.jobOffer);
	}

	public boolean isNewJobOffer() {
		return this.jobOffer.getId() == 0;
	}

	public boolean isUserHasPermissionToEditJobOffer() {
		return this.userHasPermissionToEdit(this.jobOffer);
	}

	public void onCloseClientSearchDialog(final SelectEvent e) {
		final Client clientFromDialog = (Client) e.getObject();
		if (clientFromDialog != null) {
			this.client = clientFromDialog;
		}
	}

	public void openClientSearchDialog() {
		this.openBigDialog("/faces/client/clientSearchDialog", null);
	}

	public void save() {
		this.jobOffer.setClient(this.client);
		if (this.jobOffer.getId() == 0) {
			this.insertJobOffer();
		}
		else {
			this.updateJobOffer();
		}
		this.closeDialog(this.jobOffer);
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
	}
}