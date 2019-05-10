package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Named
@ViewScoped
public class AddJobBean extends AbstractJobBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	private JobOffer buildNewJobOffer() {
		final JobOffer newJobOffer = new JobOffer();
		newJobOffer.setOwner(this.sessionUser.get());
		newJobOffer.setClient(this.getValueFromFlash("client"));
		return newJobOffer;
	}

	private void fillRecruiters(final User user) {
		final List<User> subordinateUsers = this.userService.findSubordinateUsers(user);
		this.recruiters = new ArrayList<>();
		this.recruiters.add(this.sessionUser.get().getFullName());
		for (final User subordinate : subordinateUsers) {
			this.recruiters.add(subordinate.getFullName());
		}
	}

	@PostConstruct
	public void init() {
		logger.trace("New job offer init");
		this.jobOffer = this.buildNewJobOffer();
		this.fillRecruiters(this.sessionUser.get());
	}

	public String save() {
		logger.debug("Create new client action performed");
		this.jobOffer = this.jobOfferService.create(this.jobOffer);
		return this.jobUrl();
	}
}