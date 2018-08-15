package ged.ejb.curriculum;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractAuditedEntity;

@Entity
public class Language extends AbstractAuditedEntity {

	private static final long serialVersionUID = 8362523998951126576L;

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
		return Objects.equals(this.languageName, other.languageName) && Objects.equals(this.read, other.read)
				&& Objects.equals(this.speak, other.speak) && Objects.equals(this.write, other.write);
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
		return Objects.hash(languageName, read, speak, write);
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
