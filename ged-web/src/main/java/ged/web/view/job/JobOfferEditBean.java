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

	public String cancel() {
		logger.debug("Cancel new job offer action performed");
		return "jobOfferSearch?faces-redirect=true";
	}

	public void clientChangedListener() {
		final String clientName = this.jobOffer.getClient().getName();
		logger.trace("Client name changed to {}", clientName);
		this.newClient = !this.clientService.existsClient(clientName);
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOfferEditBean init");
		this.jobOffer = new JobOffer();
		this.jobOffer.setOwner(this.sessionBean.getUser());
		this.jobOffer.setClient(new Client());
	}

	public boolean isNewClient() {
		return this.newClient;
	}

	public String save() {
		logger.debug("Save job offer action performed");
		this.jobService.save(this.jobOffer);
		return "jobOfferView.xhtml?id=" + this.jobOffer.getId() + "&faces-redirect=true";
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobService(JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setNewClient(final boolean newClient) {
		this.newClient = newClient;
	}
}