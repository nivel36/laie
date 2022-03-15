package es.nivel36.laie.ejb.curriculum.language;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractIndexableEntity;

@Indexed
@Entity
public class Language extends AbstractIndexableEntity implements Comparable<Language> {

	private static final long serialVersionUID = -3425255875950769281L;

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
}
