package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferState;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ViewJobView extends AbstractView {

	private static final long serialVersionUID = 5265865623840819856L;

	private static final Logger logger = LoggerFactory.getLogger(ViewJobView.class);

	private static String URL = "/job/view.xhtml";

	private @Param(required = true, name = "jobOffer") String jobOfferId;
	private JobOffer jobOffer;
	private boolean editable;
	private boolean bookmarkable;
	private boolean owner;
	private boolean recruiter;
	private boolean addSubmission;
	private Bookmark bookmark;

	// Pestaña de cambio de estados
	private boolean showChangeStatus = false;
	private String newState;
	private String comment;
	private List<SelectItem> states;

	private @Inject JobOfferSubmissionsLazyDataModel jobSubmissions;
	private @Inject JobOfferEventsLazyDataModel jobOfferEvents;
	private @Inject JobSubmissionEventsLazyDataModel jobSubmissionEvents;
	private @Inject JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		logger.trace("JobOffer {} init", this.jobOfferId);
		this.findJobOffer();
		this.bookmark = buildBookmark();
		this.bookmarkable = !this.sessionUser.hasBookamrk(bookmark);

		final User user = this.sessionUser.get();
		final User owner = jobOffer.getOwner();
		this.owner = user.equals(owner);
		this.recruiter = jobOffer.getRecruiters().contains(user);
		final boolean userCanEdit = this.owner || sessionUser.isAdmin() || this.sessionUser.isManagerOf(owner);
		this.editable = jobOffer.isOpen() && userCanEdit;
		this.addSubmission = jobOffer.isOpen() && (this.recruiter || userCanEdit);

		this.jobSubmissions.setJobOffer(jobOffer);
		this.jobOfferEvents.setJobOffer(jobOffer);
		this.jobSubmissionEvents.setJobOffer(jobOffer);

		fillMenuModel();
	}

	private void findJobOffer() {
		try {
			final Long id = Long.parseLong(jobOfferId);
			this.jobOffer = this.jobOfferService.findJobOfferData(id);
			if (this.jobOffer == null) {
				throw new IllegalPageStateException();
			}
		} catch (final NumberFormatException ex) {
			throw new IllegalPageStateException();
		}
	}

	public void export() {
		logger.debug("Export job action performed");
	}

	public static String getUrl(long jobId) {
		return URL + "?jobOffer=" + jobId;
	}

	public boolean isBookmarkable() {
		return bookmarkable;
	}

	public JobOfferSubmissionsLazyDataModel getJobSubmissions() {
		return this.jobSubmissions;
	}

	private Bookmark buildBookmark() {
		final Bookmark bookmark = new Bookmark();
		bookmark.setTitle(jobOffer.getTitle());
		bookmark.setUrl(ViewJobView.URL + "?jobOffer=" + this.jobOffer.getId());
		return bookmark;
	}

	public void addBookmark() {
		this.sessionUser.addBookmark(bookmark);
		this.bookmarkable = false;
	}

	public void removeFromBookmarks() {
		this.sessionUser.removeFromBookmarks(bookmark);
		this.bookmarkable = true;
	}

	public JobOfferEventsLazyDataModel getJobOfferEvents() {
		return jobOfferEvents;
	}

	public JobSubmissionEventsLazyDataModel getJobSubmissionEvents() {
		return jobSubmissionEvents;
	}

	public boolean isOwner() {
		return owner;
	}

	public boolean isAddSubmission() {
		return this.addSubmission;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public boolean isEditable() {
		return this.editable;
	}

	// Panel de cambio de estado

	public void showChangeStatus() {
		this.showChangeStatus = true;
	}

	public boolean isShowChangeStatus() {
		return this.showChangeStatus;
	}

	public void changeState(final String newState) {
		this.newState = newState;
	}

	public void changeState() {
		final JobOfferState valueOf = JobOfferState.valueOf(newState.toUpperCase());
		final User user = this.sessionUser.get();
		this.jobOffer = this.jobOfferService.changeState(jobOffer, valueOf, comment, user);
	}

	private void fillMenuModel() {
		states = new ArrayList<>();
		if (jobOffer.getState().equals(JobOfferState.OPENED)) {
			states.add(new SelectItem(JobOfferState.CLOSED.getName(), this.translator.message("job_offer_state.close")));
			states.add(new SelectItem(JobOfferState.FINISHED.getName(), this.translator.message("job_offer_state.finish")));
			states.add(new SelectItem(JobOfferState.PAUSED.getName(), this.translator.message("job_offer_state.pause")));
		} else if (jobOffer.getState().equals(JobOfferState.CLOSED)) {
			states.add(new SelectItem(JobOfferState.OPENED.getName(), this.translator.message("job_offer_state.open")));
		} else if (jobOffer.getState().equals(JobOfferState.FINISHED)) {
			states.add(new SelectItem(JobOfferState.OPENED.getName(), this.translator.message("job_offer_state.open")));
		} else if (jobOffer.getState().equals(JobOfferState.PAUSED)) {
			states.add(new SelectItem(JobOfferState.OPENED.getName(), this.translator.message("job_offer_state.open")));
			states.add(new SelectItem(JobOfferState.CLOSED.getName(), this.translator.message("job_offer_state.close")));
			states.add(new SelectItem(JobOfferState.FINISHED.getName(), this.translator.message("job_offer_state.finish")));
		} else if (jobOffer.getState().equals(JobOfferState.CREATED)) {
			states.add(new SelectItem(JobOfferState.OPENED.getName(), this.translator.message("job_offer_state.open")));
			states.add(new SelectItem(JobOfferState.CLOSED.getName(), this.translator.message("job_offer_state.close")));
			states.add(new SelectItem(JobOfferState.FINISHED.getName(), this.translator.message("job_offer_state.finish")));
			states.add(new SelectItem(JobOfferState.PAUSED.getName(), this.translator.message("job_offer_state.close")));
		}
	}
	
	public List<SelectItem> getStates() {
		return states;
	}

	public String getNewState() {
		return newState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setJobSubmissionEvents(JobSubmissionEventsLazyDataModel jobSubmissionEvents) {
		Objects.requireNonNull(jobSubmissionEvents);
		this.jobSubmissionEvents = jobSubmissionEvents;
	}

	public void setJobOfferEvents(JobOfferEventsLazyDataModel jobOfferEvents) {
		Objects.requireNonNull(jobOfferEvents);
		this.jobOfferEvents = jobOfferEvents;
	}

	public void setJobOfferService(JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}