package ged.ejb.service.candidate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class File extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "candidateId", updatable = false, nullable = false)
	private Candidate candidate;

	private Date date;
	
	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "fileTypeId", updatable = false, nullable = false)
	private FileType fileType;

	@NotNull
	@Column(nullable = false)
	private String name;

	public Candidate getCandidate() {
		return candidate;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public FileType getFileType() {
		return fileType;
	}

	public String getName() {
		return name;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public void setName(String name) {
		this.name = name;
	}

}
