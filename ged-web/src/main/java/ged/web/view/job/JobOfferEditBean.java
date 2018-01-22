package ged.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferEditBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7362448981391968171L;

	@Inject
	private transient ClientService clientService;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobService;

	private boolean newClient;

	private String returnPage;

	public String cancel() {
		logger.debug("Cancel new job offer action performed");
		if (this.returnPage != null) {
			return this.returnPage;
		} else {
			return "jobOfferSearch?faces-redirect=true";
		}
	}

	public void clientChangedListener() {
		final String clientName = this.jobOffer.getClient().getName();
		logger.trace("Client name changed to {}", clientName);
		this.newClient = !this.clientService.clientExist(clientName);
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	private String getReturnPage() {
		if (this.returnPage != null) {
			return this.returnPage;
		} else {
			return "jobOfferView.xhtml?id=" + this.jobOffer.getId() + "&faces-redirect=true";
		}
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOfferEditBean init");
		this.jobOffer = new JobOffer();
		this.jobOffer.setOwner(this.sessionBean.getUser());
		if (this.flash.containsKey("client")) {
			final Client client = (Client) this.flash.get("client");
			this.jobOffer.setClient(client);
		} else {
			this.jobOffer.setClient(new Client());
		}
		if (this.flash.containsKey("returnPage")) {
			this.returnPage = (String) this.flash.get("returnPage");
		}
	}

	public boolean isNewClient() {
		return this.newClient;
	}

	public String save() {
		logger.debug("Save job offer action performed");
		this.jobService.save(this.jobOffer);
		return getReturnPage();
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setNewClient(final boolean newClient) {
		this.newClient = newClient;
	}
}