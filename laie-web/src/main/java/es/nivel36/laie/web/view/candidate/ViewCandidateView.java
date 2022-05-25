package es.nivel36.laie.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.meeting.AddMeetingView;

@Named
@ViewScoped
public class ViewCandidateView extends AbstractView {

	private static final long serialVersionUID = 8467299017233591349L;

	private static final Logger logger = LoggerFactory.getLogger(ViewCandidateView.class);

	private static final String URL = "/candidate/view.xhtml";

	private @Param Candidate candidate;

	private boolean editable;

	private List<File> files;

	private List<JobCandidature> jobCandidatures;

	private List<Meeting> meetings;

	private boolean bookmarkable;

	private Bookmark bookmark;

	private transient @Inject CandidateService candidateService;

	private transient @Inject FileService fileUploadService;

	private transient @Inject MeetingService meetingService;

	private transient @Inject JobCandidatureService jobCandidatureService;

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			logger.warn("Candidate not found");
			throw new IllegalPageStateException();
		}
		logger.trace("Candidate {} init", this.candidate);
		this.jobCandidatures = jobCandidatureService.findCandidatesJobCandidatures(candidate, Page.ALL_RESULTS);
		final User user = this.sessionUser.get();
		final User owner = this.candidate.getOwner();
		final boolean isManagerOfoWner = this.sessionUser.isManagerOf(owner);
		this.editable = user.isAdmin() || user.equals(owner) || isManagerOfoWner;
		this.meetings = this.meetingService.findMeetingsByCandidate(this.candidate, Page.ALL_RESULTS);
		this.files = new ArrayList<>(this.candidate.getFiles());
		this.bookmark = this.buildBookmark();
		this.bookmarkable = !this.sessionUser.getBookmarks().contains(this.bookmark);
	}

	public static String getUrl(long candidateId) {
		return URL + "?candidate=" + candidateId;
	}

	private Bookmark buildBookmark() {
		final Bookmark bookmark = new Bookmark();
		bookmark.setTitle(candidate.getFullName());
		bookmark.setUrl(this.candidateUrl());
		return bookmark;
	}

	private String candidateUrl() {
		return URL + "?candidate=" + this.candidate.getId();
	}

	public void addBookmark() {
		this.sessionUser.addBookmark(bookmark);
		this.bookmarkable = false;
	}

	public void removeFromBookmarks() {
		this.sessionUser.removeFromBookmarks(bookmark);
		this.bookmarkable = true;
	}

	public void export() {
		logger.debug("Export candidate action performed");
	}

	public void newMeeting() {
		this.putValueToFlash("attendee", this.candidate);
		Faces.redirect(AddMeetingView.URL);
	}

	public void openFile(final File file) throws IOException {
		try (final InputStream is = this.fileUploadService.downloadFile(file);) {
			Faces.sendFile(is, file.getName(), true);
		}
	}

	public void removeFile(final File file) {
		this.candidate = this.candidateService.removeFileFromCandidate(this.candidate, file);
		this.files = new ArrayList<File>(candidate.getFiles());
	}

	public void uploadFile(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		logger.debug("Upload candidate {} image action performed", this.candidate);
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			this.candidate = this.candidateService.addFileToCandidate(candidate, inputStream,
					uploadedFile.getFileName());
			this.files = new ArrayList<File>(candidate.getFiles());
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

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}
}