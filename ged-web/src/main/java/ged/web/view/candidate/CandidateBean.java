package ged.web.view.candidate;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.UploadedServerFile;
import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.web.core.util.MessageUtils;
import ged.web.core.util.Navigate;
import ged.web.core.view.AbstractBean;
import ged.web.core.view.FileUploadService;

@Named
@ViewScoped
public class CandidateBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private boolean editable;

	private final List<UploadedServerFile> files = new ArrayList<>();

	@Inject
	private transient FileUploadService fileUploadService;

	private String id;

	private List<String> tags = new ArrayList<>();

	@Inject
	private transient TagService tagService;

	public Candidate buildNewCandidate() {
		final Candidate newCandidate = new Candidate();
		final Address address = new Address();
		newCandidate.setAddress(address);
		newCandidate.setOwner(this.sessionBean.getUser());
		return newCandidate;
	}

	public void cancelEditCandidate() {
		this.editable = false;
	}

	private void checkLopdFile() {
		if (!hasLopdFile()) {
			MessageUtils.addWarningMessage("candidate.warn.no_lopd_file", "candidate.warn.no_lopd_file");
		}
	}

	public void editCandidate() {
		this.editable = true;
	}

	private void error() {
		Navigate.toPage("candidateSearch");
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<UploadedServerFile> getFiles() {
		return this.files;
	}

	public String getId() {
		return this.id;
	}

	public List<String> getTags() {
		return this.tags;
	}

	// TODO: Redo
	private Set<Tag> getTagsFromStringList(final List<String> labels) {
		if (labels == null) {
			return new HashSet<>();
		}
		final Set<Tag> candidateTags = new HashSet<>();
		for (final String label : labels) {
			final List<Tag> tagsFoundInDataBase = this.tagService.search(label);
			final Tag tag;
			if (tagsFoundInDataBase.size() == 1) {
				tag = tagsFoundInDataBase.get(0);
			} else {
				tag = new Tag();
				tag.setLabel(label);
			}
			candidateTags.add(tag);
		}
		return candidateTags;
	}

	private boolean hasLopdFile() {
		if (this.candidate.getFiles() == null) {
			return false;
		}
		for (final UploadedServerFile fileSys : this.candidate.getFiles()) {
			if (fileSys.isLopd()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.id != null) {
			try {
				final long candidateId = Long.parseLong(this.id);
				this.candidate = this.candidateService.findCandidateAndFiles(candidateId);
				if (this.candidate == null) {
					error();
				}
				if (this.candidate.getAddress() == null) {
					this.candidate.setAddress(new Address());
				}
				for (final Tag tag : this.candidate.getTags()) {
					this.tags.add(tag.getLabel());
				}
				this.files.addAll(this.candidate.getFiles());
				checkLopdFile();
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			this.editable = true;
			this.candidate = buildNewCandidate();
		}
	}

	public String insertCandidate() {
		this.candidate.setTags(getTagsFromStringList(this.tags));
		this.candidate.setFiles(new HashSet<>(this.files));
		this.candidateService.insert(this.candidate);
		return "candidate.xhtml?id=" + this.candidate.getId() + "&faces-redirect=true";
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isNewCandidate() {
		return this.candidate.getId() == 0;
	}

	public String modifyCandidate() {
		this.flash.put("candidate", this.candidate);
		return "candidateEdit?faces-redirect=true";
	}

	public void onload() {
		checkLopdFile();
	}

	public void onrate(final RateEvent rateEvent) {
		final Integer rate = (Integer) rateEvent.getRating();
		this.candidate.setRating(rate);
	}

	public void openFile(final UploadedServerFile file) throws IOException {
		final File fileToOpen = this.fileUploadService.getFileFromFileSystem(file);
		Faces.sendFile(fileToOpen, true);
	}

	public void removeFile(final UploadedServerFile file) throws IOException {
		this.fileUploadService.removeFileFromFileSystem(file.getUuid());
		this.candidate.getFiles().remove(file);
		this.candidate = this.candidateService.update(this.candidate);
		this.files.remove(file);
		addInfoMessage("file.message.remove", "file.message.remove", file.getName());
	}

	private UploadedServerFile saveFile(final String uuid, final String fileName) {
		final UploadedServerFile file = new UploadedServerFile();
		file.setUuid(uuid);
		file.setName(fileName);
		file.setDate(new Date());
		if (!this.candidate.getFiles().contains(file)) {
			this.candidate.getFiles().add(file);
			file.setCandidate(this.candidate);
		}
		this.candidateService.insertFile(file);
		return file;
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public void setTagService(final TagService tagService) {
		this.tagService = tagService;
	}

	public void undelete() {
		logger.debug("UNDELETE action");
		this.candidate.setDeleted(false);
		this.candidate = this.candidateService.update(this.candidate);
	}

	public void updateCandidate() {
		this.candidate.setTags(getTagsFromStringList(this.tags));
		this.candidate.setFiles(new HashSet<>(this.files));
		this.candidate = this.candidateService.update(this.candidate);
		this.editable = false;
	}

	public void updateFile(final UploadedServerFile file) {
		this.files.remove(file);
		final UploadedServerFile updatedFile = this.candidateService.updateFile(file);
		this.files.add(updatedFile);
		addInfoMessage("file.message.update", "file.message.update", updatedFile.getName());
	}

	public void uploadFile(final FileUploadEvent event) throws IOException {
		final String uuid = this.fileUploadService.uploadFile(event.getFile());
		final UploadedServerFile file = saveFile(uuid, event.getFile().getFileName());
		this.files.add(file);
		addInfoMessage("file.message.upload", "file.message.upload", event.getFile().getFileName());
	}

	public void uploadImage(final FileUploadEvent event) throws IOException {
		final String uuid = this.fileUploadService.uploadImage(event.getFile());
		this.candidate.setImageFileName(uuid);
	}
}