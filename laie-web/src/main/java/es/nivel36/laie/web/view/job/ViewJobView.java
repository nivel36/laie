package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class ViewJobView extends AbstractView {

	private static final long serialVersionUID = 5265865623840819856L;

	private static final Logger logger = LoggerFactory.getLogger(ViewJobView.class);

	private static String URL = "/job/view.xhtml";

	private @Param(required = true) JobOffer jobOffer;

	private boolean editable;

	private boolean bookmarkable;

	private boolean owner;

	private boolean recruiter;

	private Bookmark bookmark;

	private List<JobCandidature> jobCandidatures;

	private @Inject JobOfferStateEventsLazyDataModel jobOfferStateEvents;

	@PostConstruct
	public void init() {
		logger.trace("JobOffer {} init", this.jobOffer);
		this.jobCandidatures = new ArrayList<>(jobOffer.getJobCandidatures());
		this.bookmark = buildBookmark();
		this.bookmarkable = !this.sessionUser.hasBookamrk(bookmark);

		// Hemos de inicializar los eventos con la oferta
		this.jobOfferStateEvents.setJobOffer(jobOffer);

		final User user = this.sessionUser.get();
		final User owner = jobOffer.getOwner();
		this.owner = user.equals(owner);
		this.recruiter = jobOffer.getRecruiters().contains(user);
		this.editable = !jobOffer.getState().isCloseState() && isOwner();
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

	public List<JobCandidature> getJobCandidatures() {
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

	public JobOfferStateEventsLazyDataModel getJobOfferStateEvents() {
		return jobOfferStateEvents;
	}

	public boolean isOwner() {
		return owner;
	}

	public boolean isAddCandidature() {
		return jobOffer.isOpen() && (recruiter || owner);
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public boolean isEditable() {
		return this.editable;
	}
}