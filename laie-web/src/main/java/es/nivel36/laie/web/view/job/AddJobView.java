package es.nivel36.laie.web.view.job;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.job.offer.JobOffer;

@Named
@ViewScoped
public class AddJobView extends AbstractJobView {

	private static final long serialVersionUID = -1123122413333334446L;

	private static final Logger logger = LoggerFactory.getLogger(AddJobView.class);

	private Client client;

	private boolean publish;

	@PostConstruct
	public void init() {
		logger.trace("New job offer init");
		this.jobOffer = new JobOffer();
		this.jobOffer.setOpenDate(LocalDate.now());
		this.recruiters = this.sessionUser.getTeam();
	}

	public void onClientSelect(final SelectEvent<Client> event) {
		final Client clientFromEvent = event.getObject();
		if (clientFromEvent != null) {
			this.client = clientFromEvent;
		}
	}

	public void save() {
		logger.debug("Create new client action performed");
		this.jobOfferService.addJobOffer(jobOffer);
		if (publish) {
			this.jobOfferService.publish(this.jobOffer);
		}
		Faces.redirect(ViewJobView.URL + "?jobOffer=" + this.jobOffer.getId());
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}
}