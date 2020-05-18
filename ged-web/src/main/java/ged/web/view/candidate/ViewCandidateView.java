package ged.web.view.candidate;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
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

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.file.File;
import ged.ejb.core.file.FileService;
import ged.ejb.core.model.Address;
import ged.ejb.core.model.Page;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ViewCandidateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private boolean editable;

	private List<File> files;

	@Inject
	private transient FileService fileUploadService;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	private List<Meeting> meetings;

	@Inject
	private transient MeetingService meetingService;

	public String editCandidate() {
		logger.debug("Edit candidate action performed");
		return this.navigator.getRedirectUrl(PageEnum.CANDIDATE_EDIT, this.candidate);
	}

	public void export() {
		logger.debug("Export candidate action performed");
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
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

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Candidate {} init", this.candidate);
		if (this.candidate.getAddress() == null) {
			this.candidate.setAddress(new Address());
		}
		this.jobCandidatures = this.jobCandidatureService.findJobCandidatures(this.candidate, Page.ALL_RESULTS);
		this.curriculum = this.curriculumService.findByCandidate(this.candidate);
		this.editable = this.sessionUser.hasPermissionToEdit(this.candidate);
		this.meetings = this.initMeetings();
		this.files = this.candidateService.findCandidatesFiles(this.candidate, Page.TEN_RESULTS_PER_PAGE);
	}

	private List<Meeting> initMeetings() {
		return this.meetingService.findMeetings(this.candidate, Page.TEN_RESULTS_PER_PAGE);
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String newMeeting() {
		this.putValueToFlash("attendee", this.candidate);
		return this.navigator.getRedirectUrl(PageEnum.MEETING_ADD);
	}

	public void openFile(final File file) throws IOException {
		try (final InputStream is = this.fileUploadService.getFile(file);) {
			Faces.sendFile(is, file.getName(), true);
		}
	}

	public void removeFile(final File file) {
		this.candidate = this.candidateService.removeFileFromCandidate(candidate, file);
		this.files.remove(file);
	}

	public void selectJobOffer() {
		logger.debug("Select job action performed");
		this.openBigDialog(PageEnum.JOB_SELECT.getUrl());
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setFileUploadService(final FileService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void uploadFile(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		logger.debug("Upload candidate {} image action performed", this.candidate);
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			this.candidateService.addFileToCandidate(candidate, inputStream, uploadedFile.getFileName());
		}
	}
}