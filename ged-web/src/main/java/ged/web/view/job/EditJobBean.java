package ged.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;
import ged.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditJobBean extends AbstractJobBean {

	private static final String JOB_OFFER_KEY = "jobOffer";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4373329969104383876L;

	private void checkEditPermission() {
		if (!this.sessionUser.hasPermissionToEdit(this.jobOffer)) {
			logger.error("User {} hasn't got priviliges to edit jobOffer {}", this.sessionUser.get(), this.jobOffer);
			throw new SecurityException();
		}
	}

	private void checkNonNullJobOffer() {
		if (this.jobOffer == null) {
			logger.error("Trying to edit a job offer but job is null");
			throw new IllegalPageStateException();
		}
	}

	private void fillRecruiters() {
		for (final User recruiter : this.jobOffer.getRecruiters()) {
			this.getRecruiters().add(recruiter.getFullName());
		}
	}

	@PostConstruct
	public void init() {
		this.jobOffer = this.getValueFromFlash(JOB_OFFER_KEY);
		this.checkNonNullJobOffer();
		this.checkEditPermission();
		this.putValueToFlash(JOB_OFFER_KEY, this.jobOffer); // prevent errors if f5/reload is pressed
		logger.trace("Edit job offer {} init", this.jobOffer);
		this.fillRecruiters();
	}

	public String save() {
		logger.debug("Save job offer action performed");
		this.jobOffer = this.jobOfferService.save(this.jobOffer);
		return this.jobUrl();
	}
}