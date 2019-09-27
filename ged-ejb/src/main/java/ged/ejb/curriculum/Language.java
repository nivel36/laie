package ged.ejb.curriculum;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.model.AbstractEntity;

@Indexed
@Entity
public class Language extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LanguageLevel level;

	@NotNull
	@Column(nullable = false)
	@Field
	private String name;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Language other = (Language) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.level, other.level);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public LanguageLevel getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.level);
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setLevel(final LanguageLevel level) {
		this.level = level;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Language [languageName=" + this.name + ", level=" + this.level + "]";
	}
}
