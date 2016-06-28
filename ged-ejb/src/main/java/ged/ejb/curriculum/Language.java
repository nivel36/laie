package ged.ejb.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractAuditedEntity;

@Entity
public class Language extends AbstractAuditedEntity {

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

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Language other = (Language) obj;
		if (this.languageName == null) {
			if (other.languageName != null) {
				return false;
			}
		} else if (!this.languageName.equals(other.languageName)) {
			return false;
		}
		if (!this.read.equals(other.read)) {
			return false;
		}
		if (!this.speak.equals(other.speak)) {
			return false;
		}
		if (!this.write.equals(other.write)) {
			return false;
		}
		return true;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getLanguageName() {
		return this.languageName;
	}

	public String getRead() {
		return this.read;
	}

	public String getSpeak() {
		return this.speak;
	}

	public String getWrite() {
		return this.write;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.languageName == null) ? 0 : this.languageName.hashCode());
		result = (prime * result) + ((this.read == null) ? 0 : this.read.hashCode());
		result = (prime * result) + ((this.speak == null) ? 0 : this.speak.hashCode());
		result = (prime * result) + ((this.write == null) ? 0 : this.write.hashCode());
		return result;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setLanguageName(final String languageName) {
		this.languageName = languageName;
	}

	public void setRead(final String read) {
		this.read = read;
	}

	public void setSpeak(final String speak) {
		this.speak = speak;
	}

	public void setWrite(final String write) {
		this.write = write;
	}

	@Override
	public String toString() {
		return "Language [languageName=" + this.languageName + ", speak=" + this.speak + ", write=" + this.write
				+ ", read=" + this.read + "]";
	}
}
