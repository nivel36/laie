package ged.ejb.curriculum;

import java.util.Date;
import java.util.Objects;

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

	@NotNull
	@I18n
	private String fileType;

	@NotNull
	@Column(nullable = false)
	private String name;

	private String uuid;

	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (this == obj){
			return true;
		}
		if (!super.equals(obj)){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		FileSys other = (FileSys) obj;
		return Objects.equals(this.uuid, other.uuid);
	}

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

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
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

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}