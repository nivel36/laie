package ged.ejb.curriculum;

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
		final Skill other = (Skill) obj;
		if (this.level != other.level) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.level == null) ? 0 : this.level.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
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

	@Override
	public String toString() {
		return "Skill [level=" + this.level + ", name=" + this.name + "]";
	}
}