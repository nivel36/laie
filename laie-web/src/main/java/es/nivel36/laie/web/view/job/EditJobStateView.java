package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.event.JobOfferEventService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class EditJobStateView extends AbstractView {

	private static final long serialVersionUID = -3933919096807704912L;

	private static final Logger logger = LoggerFactory.getLogger(EditJobStateView.class);

	@Param(required = true)
	private JobOffer jobOffer;

	private String notes;

	private JobOfferState state;

	private List<SelectItem> states;

	@Inject
	protected transient JobOfferEventService jobOfferEventService;

	@Inject
	protected transient JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		logger.trace("Edit job state {} init", this.jobOffer);
		states = new ArrayList<>();
		if (jobOffer.getState().equals(JobOfferState.OPENED)) {
			states.add(new SelectItem(JobOfferState.CLOSED, this.translator.message("job_offer_state.close")));
			states.add(new SelectItem(JobOfferState.FINISHED, this.translator.message("job_offer_state.finish")));
			states.add(new SelectItem(JobOfferState.PAUSED, this.translator.message("job_offer_state.pause")));
		} else if (jobOffer.getState().equals(JobOfferState.CLOSED)) {
			states.add(new SelectItem(JobOfferState.OPENED, this.translator.message("job_offer_state.open")));
		} else if (jobOffer.getState().equals(JobOfferState.FINISHED)) {
			states.add(new SelectItem(JobOfferState.OPENED, this.translator.message("job_offer_state.open")));
		} else if (jobOffer.getState().equals(JobOfferState.PAUSED)) {
			states.add(new SelectItem(JobOfferState.OPENED, this.translator.message("job_offer_state.open")));
			states.add(new SelectItem(JobOfferState.CLOSED, this.translator.message("job_offer_state.close")));
			states.add(new SelectItem(JobOfferState.FINISHED, this.translator.message("job_offer_state.finish")));
		} else if (jobOffer.getState().equals(JobOfferState.CREATED)) {
			states.add(new SelectItem(JobOfferState.OPENED, this.translator.message("job_offer_state.open")));
			states.add(new SelectItem(JobOfferState.CLOSED, this.translator.message("job_offer_state.close")));
			states.add(new SelectItem(JobOfferState.FINISHED, this.translator.message("job_offer_state.finish")));
			states.add(new SelectItem(JobOfferState.PAUSED, this.translator.message("job_offer_state.close")));
		}
	}

	public List<SelectItem> getStates() {
		return states;
	}

	public void save() {
		logger.debug("Save job offer action performed");
		this.jobOfferService.updateJobOffer(this.jobOffer);
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
