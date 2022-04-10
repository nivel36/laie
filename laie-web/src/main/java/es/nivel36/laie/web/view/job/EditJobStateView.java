package es.nivel36.laie.web.view.job;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.event.JobOfferEventService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class EditJobStateView extends AbstractView {

	private static final long serialVersionUID = -3933919096807704912L;

	private static final Logger logger = LoggerFactory.getLogger(EditJobStateView.class);

	private JobOffer jobOffer;

	private String notes;

	private JobOfferState state;

	@Inject
	protected transient JobOfferEventService jobOfferEventService;
	
	@Inject
	protected transient JobOfferService jobOfferService;
	
	@PostConstruct
	public void init() {
		final String Id = this.getValueFromGetParameters("jobOffer", true);
		this.jobOffer = this.jobOfferService.findJobOfferById(Id);
		this.checkNonNullJobOffer();
		logger.trace("Edit job state {} init", this.jobOffer);
	}

	private void checkNonNullJobOffer() {
		if (this.jobOffer == null) {
			logger.error("Trying to edit a job offer but job is null");
			throw new IllegalPageStateException();
		}
	}

	private String jobUrl() {
		return null;
	}

	public String save() {
		logger.debug("Save job offer action performed");
		return this.jobUrl();
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

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public void setState(final JobOfferState state) {
		this.state = state;
	}
	
	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
	
	public void setJobOfferEventService(final JobOfferEventService jobOfferEventService) {
		Objects.requireNonNull(jobOfferEventService);
		this.jobOfferEventService = jobOfferEventService;
	}
}
