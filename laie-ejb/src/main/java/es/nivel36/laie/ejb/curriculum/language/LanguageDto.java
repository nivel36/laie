package es.nivel36.laie.ejb.curriculum.language;

import java.io.Serializable;
import java.util.Objects;

import es.nivel36.laie.ejb.curriculum.Curriculum;

public class LanguageDto implements Serializable, Comparable<LanguageDto> {

	private static final long serialVersionUID = 2414298258304492807L;

	private Curriculum curriculum;

	private LanguageLevel level;

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
		final LanguageDto other = (LanguageDto) obj;
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
		return "Language [" + this.name + ", level=" + this.level + "]";
	}

	@Override
	public int compareTo(LanguageDto language) {
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
