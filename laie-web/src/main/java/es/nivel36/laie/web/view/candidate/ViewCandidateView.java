package es.nivel36.laie.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.core.bookmark.BookmarkService;
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.meeting.AddMeetingView;

@Named
@ViewScoped
public class ViewCandidateView extends AbstractView {

	private static final long serialVersionUID = 8467299017233591349L;

	private static final Logger logger = LoggerFactory.getLogger(ViewCandidateView.class);

	public static final String URL = "/candidate/candidate.xhtml";

	@Param(name = "candidate", converter = "candidateConverter")
	private Candidate candidate;

	private boolean editable;

	private List<File> files;

	private List<JobCandidature> jobCandidatures;

	private List<Meeting> meetings;

	private boolean bookmarkable;

	private Bookmark bookmark;

	@Inject
	private transient BookmarkService bookmarkService;

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient FileService fileUploadService;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	@Inject
	private transient MeetingService meetingService;

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Candidate {} init", this.candidate);
		this.jobCandidatures = this.jobCandidatureService.findCandidatesJobCandidatures(this.candidate,
				Page.ALL_RESULTS);
		this.editable = true;
		this.meetings = this.meetingService.findMeetingsByCandidate(this.candidate, Page.TEN_RESULTS_PER_PAGE);
		this.files = this.candidateService.findCandidatesFiles(this.candidate, Page.TEN_RESULTS_PER_PAGE);
		this.bookmark = this.buildBookmark();
		this.bookmarkable = !this.sessionUser.getBookmarks().contains(this.bookmark);
	}

	private Bookmark buildBookmark() {
		final Bookmark bookmark = new Bookmark();
		bookmark.setTitle(candidate.getName());
		bookmark.setUrl(this.candidateUrl());
		return bookmark;
	}

	protected String candidateUrl() {
		return URL + "?candidate=" + this.candidate.getId();
	}

	public void addBookmark() {
		this.bookmarkService.addBookmark(this.bookmark);
		this.bookmarkable = false;
		this.sessionUser.getBookmarks().add(bookmark);
	}

	public void removeFromBookmarks() {
		this.bookmarkService.deleteBookmark(this.bookmark);
		this.bookmarkable = true;
		this.sessionUser.getBookmarks().remove(bookmark);
	}

	public void export() {
		logger.debug("Export candidate action performed");
	}

	public void newMeeting() {
		this.putValueToFlash("attendee", this.candidate);
		this.navigateTo(AddMeetingView.URL);
	}

	public void openFile(final File file) throws IOException {
		try (final InputStream is = this.fileUploadService.downloadFile(file);) {
			Faces.sendFile(is, file.getName(), true);
		}
	}

	public void removeFile(final File file) {
		this.candidateService.removeFileFromCandidate(candidate, file);
		this.files.remove(file);
	}

	public void uploadFile(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		logger.debug("Upload candidate {} image action performed", this.candidate);
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			final File file = this.candidateService.addFileToCandidate(candidate, inputStream,
					uploadedFile.getFileName());
			this.files.add(file);
		}
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isBookmarkable() {
		return this.bookmarkable;
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<File> getFiles() {
		return this.files;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public List<Meeting> getMeetings() {
		return this.meetings;
	}

	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}

	public void setFileUploadService(final FileService fileUploadService) {
		Objects.requireNonNull(fileUploadService);
		this.fileUploadService = fileUploadService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}
}