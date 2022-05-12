package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
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
	
	@Param(required = true)
	private JobOffer jobOffer;

	private boolean editable;

	private boolean bookmarkable;

	private Bookmark bookmark;

	private List<JobCandidature> jobCandidatures;

	@PostConstruct
	public void init() {
		logger.trace("JobOffer {} init", this.jobOffer);
		this.jobCandidatures = new ArrayList<>(jobOffer.getJobCandidatures());
		this.bookmark = buildBookmark();
		this.bookmarkable = !this.sessionUser.hasBookamrk(bookmark);
		final User user = this.sessionUser.get();
		this.editable = jobOffer.getRecruiters().contains(user) || jobOffer.getOwner().equals(user);
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
		bookmark.setUser(this.sessionUser.get());
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
	
	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public boolean isEditable() {
		return this.editable;
	}
}