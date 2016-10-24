package ged.web.view.job;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferEditBean extends AbstractPageBean {

	private static final long serialVersionUID = 7362448981391968171L;

	private final transient ClientService clientService;

	private JobOffer jobOffer;

	private final transient JobOfferService jobService;

	private boolean newClient;

	@Inject
	public JobOfferEditBean(final JobOfferService jobService, final ClientService clientService) {
		if (jobService == null) {
			throw new NullPointerException();
		}
		if (clientService == null) {
			throw new NullPointerException();
		}
		this.jobService = jobService;
		this.clientService = clientService;
	}

	public String cancel() {
		return "jobOfferSearch?faces-redirect=true";
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		if (this.flash.containsKey("jobOffer")) {
			this.jobOffer = (JobOffer) this.flash.get("jobOffer");
		} else {
			this.jobOffer = new JobOffer();
			final Client client = new Client();
			this.jobOffer.setClient(client);
		}
	}

	public boolean isNewClient() {
		return this.newClient;
	}

	public String save() {
		saveJobOffer();
		return "jobOfferView.xhtml?id=" + this.jobOffer.getId() + "&faces-redirect=true";
	}

	private void saveJobOffer() {
		this.jobOffer.setUser(this.sessionBean.getUser());
		if (this.jobOffer.getOwner() == null) {
			this.jobOffer.setOwner(this.sessionBean.getUser());
		}
		this.jobService.insertOrUpdate(this.jobOffer);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setNewClient(final boolean newClient) {
		this.newClient = newClient;
	}

	public void updateClientState() {
		this.newClient = !this.clientService.existsClient(this.jobOffer.getClient().getName());
	}
}