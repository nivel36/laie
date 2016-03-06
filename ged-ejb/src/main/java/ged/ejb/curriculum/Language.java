package ged.ejb.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AuditedEntity;

@Entity
public class Language extends AuditedEntity {

	private static final long serialVersionUID = 8362523998951126576L;;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@NotNull
	@Column(nullable = false, length = 64)
	private String languageName;

	@NotNull
	@I18n
	private String read;

	@NotNull
	@I18n
	private String speak;

	@NotNull
	@I18n
	private String write;

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getLanguageName() {
		return languageName;
	}

	public String getRead() {
		return read;
	}

	public String getSpeak() {
		return speak;
	}

	public String getWrite() {
		return write;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public void setSpeak(String speak) {
		this.speak = speak;
	}

	public void setWrite(String write) {
		this.write = write;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (languageName == null) {
			if (other.languageName != null)
				return false;
		} else if (!languageName.equals(other.languageName))
			return false;
		if (read != other.read)
			return false;
		if (speak != other.speak)
			return false;
		if (write != other.write)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((languageName == null) ? 0 : languageName.hashCode());
		result = prime * result + ((read == null) ? 0 : read.hashCode());
		result = prime * result + ((speak == null) ? 0 : speak.hashCode());
		result = prime * result + ((write == null) ? 0 : write.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Language [languageName=" + languageName + ", speak=" + speak
				+ ", write=" + write + ", read=" + read + "]";
	}
}
