package es.nivel36.laie.ejb.curriculum;

import java.util.Objects;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Indexed
@Entity
@Table(name = "LANGUAGE")
public class Language extends AbstractEntity implements Comparable<Language> {

	private static final long serialVersionUID = -3425255875950769281L;

	@ManyToOne
	@JoinColumn(name = "CURRICULUM_ID")
	private Curriculum curriculum;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "LEVEL", nullable = false)
	private LanguageLevel level;

	@NotNull
	@FullTextField
	@Column(name = "NAME", nullable = false)
	private String name;

	@Override
	public int compareTo(Language language) {
		if (this.level.equals(language.level)) {
			return this.name.compareTo(language.name);
		}
		if (this.level.equals(LanguageLevel.NATIVE)) {
			return 1;
		}
		if (language.level.equals(LanguageLevel.NATIVE)) {
			return -1;
		}
		if (this.level.equals(LanguageLevel.HIGH)) {
			return 1;
		}
		if (language.level.equals(LanguageLevel.HIGH)) {
			return -1;
		}
		if (this.level.equals(LanguageLevel.MEDIUM)) {
			return 1;
		}
		if (language.level.equals(LanguageLevel.MEDIUM)) {
			return -1;
		}
		return 0;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final Language other = (Language) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.level, other.level);
	}

	public Curriculum getCurriculum() {
		return curriculum;
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

	public void setCurriculum(Curriculum curriculum) {
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
