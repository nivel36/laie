package es.nivel36.laie.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Page;
import es.nivel36.files.File;
import es.nivel36.files.FileDto;
import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.AddressDto;
import es.nivel36.laie.ejb.candidate.CandidateDto;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.bookmark.BookmarkDto;
import es.nivel36.laie.ejb.core.bookmark.BookmarkService;
import es.nivel36.laie.ejb.curriculum.CurriculumDto;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.meeting.AddMeetingView;

@Named
@ViewScoped
public class ViewCandidateView extends AbstractView {

	private static final long serialVersionUID = 8467299017233591349L;

	private static final Logger logger = LoggerFactory.getLogger(ViewCandidateView.class);

	private CandidateDto candidate;

	private String uid;

	private CurriculumDto curriculum;

	private boolean editable;

	private List<FileDto> files;

	private List<JobCandidatureDto> jobCandidatures;

	private List<MeetingDto> meetings;
	
	private boolean bookmarkable;

	private BookmarkDto bookmark;
	
	@Inject
	private transient BookmarkService bookmarkService;

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient CurriculumService curriculumService;

	@Inject
	private transient FileService fileUploadService;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	@Inject
	private transient MeetingService meetingService;

	@PostConstruct
	public void init() {
		this.uid = this.getValueFromGetParameters("candidate");
		if (this.uid == null) {
			throw new IllegalPageStateException();
		}
		this.candidate = candidateService.findCandidateByUid(this.uid);
		logger.trace("Candidate {} init", this.candidate);
		if (this.candidate.getAddress() == null) {
			final AddressDto address = new AddressDto();
			this.candidate.setAddress(address);
		}
		this.jobCandidatures = this.jobCandidatureService.findCandidatesJobCandidatures(this.uid, Page.ALL_RESULTS);
		this.curriculum = this.curriculumService.findCandidatesCurriculum(this.uid);
		this.editable = true;
		this.meetings = this.initMeetings();
		this.files = this.findFiles();
		this.bookmark = this.buildBookmark();
		this.bookmarkable = !this.sessionUser.getBookmarks().contains(this.bookmark);
	}

	private BookmarkDto buildBookmark() {
		final BookmarkDto bookmark = new BookmarkDto();
		bookmark.setTitle(candidate.getName());
		bookmark.setUrl(this.candidateUrl());
		return bookmark;
	}
	
	protected String candidateUrl() {
		return "/candidate/candidate.xhtml?candidate=" + this.candidate.getUid();
	}
	
	public void addToBookmarks() {
		this.bookmarkService.addBookmark(this.bookmark, this.candidate.getUid());
		this.bookmarkable = false;
		this.sessionUser.refresh();
	}

	public void removeFromBookmarks() {
		this.bookmarkService.deleteBookmark(this.bookmark, this.candidate.getUid());
		this.bookmarkable = true;
		this.sessionUser.refresh();
	}

	private List<MeetingDto> initMeetings() {
		return this.meetingService.findMeetingsByCandidate(this.uid, Page.TEN_RESULTS_PER_PAGE);
	}

	private List<FileDto> findFiles() {
		return this.candidateService.findCandidatesFiles(this.uid, Page.TEN_RESULTS_PER_PAGE);
	}

	public void export() {
		logger.debug("Export candidate action performed");
	}

	public void newMeeting() {
		this.putValueToFlash("attendee", this.candidate);
		this.navigateTo(AddMeetingView.URL);
	}

	public void openFile(final File file) throws IOException {
		try (final InputStream is = this.fileUploadService.downloadFile(file.getUid());) {
			Faces.sendFile(is, file.getName(), true);
		}
	}

	public void removeFile(final FileDto file) {
		this.candidateService.removeFileFromCandidate(candidate.getUid(), file.getUid());
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
			final FileDto file = this.candidateService.addFileToCandidate(candidate.getUid(), inputStream,
					uploadedFile.getFileName());
			this.files.add(file);
		}
	}

	public void setCandidate(final CandidateDto candidate) {
		this.candidate = candidate;
	}

	public boolean isEditable() {
		return this.editable;
	}
	
	public boolean isBookmarkable() {
		return this.bookmarkable;
	}

	public CandidateDto getCandidate() {
		return this.candidate;
	}

	public CurriculumDto getCurriculum() {
		return this.curriculum;
	}

	public List<FileDto> getFiles() {
		return this.files;
	}

	public List<JobCandidatureDto> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public List<MeetingDto> getMeetings() {
		return this.meetings;
	}

	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
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