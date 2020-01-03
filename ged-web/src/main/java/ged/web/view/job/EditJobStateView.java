package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Address;
import ged.ejb.event.JobOfferEvent;
import ged.ejb.event.JobOfferEventService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferState;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class EditJobStateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private JobOffer jobOffer;

	@Inject
	protected transient JobOfferEventService jobOfferEventService;

	private String notes;

	private JobOfferState state;

	private JobOfferEvent buildEvent() {
		final JobOfferEvent event = new JobOfferEvent();
		event.setJobOffer(this.jobOffer);
		event.setDate(LocalDateTime.now());
		event.setNotes(this.notes);
		event.setState(this.state);
		event.setUser(this.sessionUser.get());
		return event;
	}

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

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public String getNotes() {
		return this.notes;
	}

	public JobOfferState getState() {
		return this.state;
	}

	@PostConstruct
	public void init() {
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
		final JobOfferEvent event = this.buildEvent();
		this.jobOfferEventService.save(event);
		return this.jobUrl();
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferEventService(final JobOfferEventService jobOfferEventService) {
		this.jobOfferEventService = jobOfferEventService;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setState(final JobOfferState state) {
		this.state = state;
	}
}
