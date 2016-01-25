package ged.ejb.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.AbstractEntity;
import ged.ejb.core.i18n.I18n;

@Entity
public class Skill extends AbstractEntity {

	private static final long serialVersionUID = 6591356212002981267L;;

	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false, updatable = false)
	private Curriculum curriculum;

	@NotNull
	@I18n
	private String level;

	@NotNull
	@Column(nullable = false)
	private String name;	

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skill other = (Skill) obj;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Skill [level=" + level + ", name=" + name + "]";
	}
}
