package es.nivel36.laie.web.view.job;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.SimpleUser;
import es.nivel36.laie.ejb.user.User;

@Named
@ViewScoped
public class AddJobView extends AbstractJobView {

	private static final long serialVersionUID = -1123122413333334446L;

	private static final Logger logger = LoggerFactory.getLogger(AddJobView.class);

	private Client client;

	private SimpleUser owner;

	private boolean publish;

	@PostConstruct
	public void init() {
		logger.trace("New job offer init");
		this.jobOffer = new JobOffer();
		this.jobOffer.setOpenDate(LocalDate.now());
		final User User = this.sessionUser.get();
		this.owner = new SimpleUser(User);
		this.fillRecruiters(User);
	}

	public void onClientSelect(final SelectEvent<Client> event) {
		final Client  = event.getObject();
		if ( != null) {
			this.client = ;
		}
	}

	private void fillRecruiters(final User user) {
		final List<User> subordinateUsers = this.userService.findSubordinateUsers(user.getId());
		for (final User subordinate : subordinateUsers) {
			final SimpleUser simpleUser = new SimpleUser(subordinate);
			this.recruiters.add(simpleUser);
		}
	}

	public void save() {
		logger.debug("Create new client action performed");
		String[] recruitersId = this.recruiters.stream().map(SimpleUser::getId).toArray(String[]::new);
		this.jobOffer = this.jobOfferService.addJobOffer(client.getId(), this.sessionUser.get().getId(),
				recruitersId, jobOffer);
		if (publish) {
			this.jobOfferService.publish(this.jobOffer.getId());
		}
		Faces.redirect(ViewJobView.URL + "?job=" + this.jobOffer.getId());
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public final SimpleUser getOwner() {
		return owner;
	}

	public final void setOwner(SimpleUser owner) {
		this.owner = owner;
	}

	public final boolean isPublish() {
		return publish;
	}

	public final void setPublish(boolean publish) {
		this.publish = publish;
	}
}