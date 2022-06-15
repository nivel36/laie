package es.nivel36.laie.web.view.job;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

@Named
@ViewScoped
public class AddJobView extends AbstractJobView {

	private static final long serialVersionUID = -1123122413333334446L;

	private static final Logger logger = LoggerFactory.getLogger(AddJobView.class);

	@Param
	private Client client;

	@PostConstruct
	public void init() {
		logger.trace("New job offer init");
		this.jobOffer = new JobOffer();
		if (client != null) {
			this.jobOffer.setClient(client);
			this.jobOffer.setAddress(client.getAddress());
		}
		this.jobOffer.setOpenDate(LocalDate.now());
		this.jobOffer.setOwner(this.sessionUser.get());
		this.recruiters = this.sessionUser.getTeam().stream().map(User::getEmail).collect(Collectors.toList());
	}

	public void next() {
		logger.debug("Create new job Offer action performed");
		this.convertRecruiters();
		this.jobOfferService.addJobOffer(this.jobOffer);
		Faces.redirect("/job/addDetails.xhtml?jobOffer=" + this.jobOffer.getId());
	}

	private boolean canAddJobOfferToClient() {
		final User user = sessionUser.get();
		final User owner = client.getOwner();
		final boolean isOwnersTeam = userService.isSubordinateUser(user, owner);
		return sessionUser.isAdmin() || user.equals(owner) || isOwnersTeam;
	}

	public void save() {
		if (canAddJobOfferToClient()) {
			this.jobOfferService.updateJobOffer(this.jobOffer);
			Faces.redirect(ViewJobView.getUrl(this.jobOffer.getId()));
		} else {
			this.addErrorToField("jobOfferForm:client", "job.error.no_permissions");
		}
	}

	public void setClient(final Client client) {
		this.client = client;
	}
}