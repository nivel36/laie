package es.nivel36.laie.web.view.job;

import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
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
	private boolean addCandidature;
	private Bookmark bookmark;
	private MenuModel menuModel;

	private boolean showChangeStatus = false;

	private @Inject JobOfferCandidaturesLazyDataModel jobCandidatures;
	private @Inject JobOfferEventsLazyDataModel jobOfferEvents;
	private @Inject JobCandidatureEventsLazyDataModel jobCandidatureEvents;
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
		this.addCandidature = jobOffer.isOpen() && (this.recruiter || userCanEdit);

		this.jobCandidatures.setJobOffer(jobOffer);
		this.jobOfferEvents.setJobOffer(jobOffer);
		this.jobCandidatureEvents.setJobOffer(jobOffer);

		this.menuModel = new DefaultMenuModel();
		fillMenuModel();
	}

	public void showChangeStatus() {
		this.showChangeStatus = true;
	}

	public boolean isShowChangeStatus() {
		return this.showChangeStatus;
	}

	private DefaultMenuItem buildMenuItem(final String text, final JobOfferState state) {
		return DefaultMenuItem.builder().value(this.translator.message(text)).escape(true)
				.command("#{viewJobView.changeState('" + state.getName() + "')}").ajax(true)
				.update("@this :openDate :closeDate").process("@this").build();
	}

	public void changeState(final String newState) {
		this.jobOffer = this.jobOfferService.changeState(jobOffer, JobOfferState.valueOf(newState.toUpperCase()), null,
				sessionUser.getUser());
		fillMenuModel();
	}

	private void fillMenuModel() {
		menuModel.getElements().clear();
		if (jobOffer.getState().equals(JobOfferState.OPENED)) {
			menuModel.getElements().add(buildMenuItem("job_offer_state.pause", JobOfferState.PAUSED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.close", JobOfferState.CLOSED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.finish", JobOfferState.FINISHED));
		} else if (jobOffer.getState().equals(JobOfferState.CLOSED)) {
			menuModel.getElements().add(buildMenuItem("job_offer_state.open", JobOfferState.OPENED));
		} else if (jobOffer.getState().equals(JobOfferState.FINISHED)) {
			menuModel.getElements().add(buildMenuItem("job_offer_state.pause", JobOfferState.PAUSED));
		} else if (jobOffer.getState().equals(JobOfferState.PAUSED)) {
			menuModel.getElements().add(buildMenuItem("job_offer_state.open", JobOfferState.OPENED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.close", JobOfferState.CLOSED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.finish", JobOfferState.FINISHED));
		} else if (jobOffer.getState().equals(JobOfferState.CREATED)) {
			menuModel.getElements().add(buildMenuItem("job_offer_state.open", JobOfferState.OPENED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.pause", JobOfferState.PAUSED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.close", JobOfferState.CLOSED));
			menuModel.getElements().add(buildMenuItem("job_offer_state.finish", JobOfferState.FINISHED));
		}
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

	public JobOfferCandidaturesLazyDataModel getJobCandidatures() {
		return this.jobCandidatures;
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

	public JobCandidatureEventsLazyDataModel getJobCandidatureEvents() {
		return jobCandidatureEvents;
	}

	public boolean isOwner() {
		return owner;
	}

	public boolean isAddCandidature() {
		return this.addCandidature;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public MenuModel getMenuModel() {
		return this.menuModel;
	}

	public void setJobCandidatureEvents(JobCandidatureEventsLazyDataModel jobCandidatureEvents) {
		Objects.requireNonNull(jobCandidatureEvents);
		this.jobCandidatureEvents = jobCandidatureEvents;
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