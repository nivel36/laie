package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Address;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.job.offer.JobOfferState;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class EditJobStateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private JobOffer jobOffer;

	@Inject
	protected transient JobOfferService jobOfferService;

	private JobOfferState newState;

	private String note;

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

	public JobOfferState getNewState() {
		return this.newState;
	}

	public String getNote() {
		return this.note;
	}

	@PostConstruct
	public void init() {
		this.jobOffer = this.getValueFromFlash("jobOffer");
		this.checkNonNullJobOffer();
		this.checkEditPermission();
		if (this.jobOffer.getAddress() == null) {
			this.jobOffer.setAddress(new Address());
		}
		logger.trace("Edit job offer {} init", this.jobOffer);
	}

	private String jobUrl() {
		return PageEnum.JOB.getRedirectedUrl(this.jobOffer);
	}

	public String save() {
		logger.debug("Save job offer action performed");
		this.jobOffer = this.jobOfferService.save(this.jobOffer);
		return this.jobUrl();
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setNewState(final JobOfferState newState) {
		this.newState = newState;
	}

	public void setNote(final String note) {
		this.note = note;
	}
}
