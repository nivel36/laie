package ged.ejb.curriculum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;

@Entity
public class FileSys extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "candidateId", nullable = false)
	private Candidate candidate;

	private Date date;
	
	private String description;
	
	private String uuid;

	@NotNull
	@I18n
	private String fileType;

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

	public String getFileType() {
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

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
