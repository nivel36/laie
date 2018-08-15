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
public class Skill extends AbstractAuditedEntity {

	private static final long serialVersionUID = 6591356212002981267L;

	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@NotNull
	@I18n
	private String level;

	@NotNull
	@Column(nullable = false)
	private String name;

	
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
		final Skill other = (Skill) obj;
		return Objects.equals(this.level, other.level) && Objects.equals(this.name, other.name);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	
	public int hashCode() {
		return Objects.hash(level, name);
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public void setName(final String name) {
		this.name = name;
	}

	
	public String toString() {
		return "Skill [level=" + this.level + ", name=" + this.name + "]";
	}
}