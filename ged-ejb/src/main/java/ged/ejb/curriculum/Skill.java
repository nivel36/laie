package ged.ejb.curriculum;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.model.AbstractEntity;

@Entity
@Indexed
public class Skill extends AbstractEntity {

	private static final long serialVersionUID = 6591356212002981267L;

	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@Field
	@NotNull
	@Column(nullable = false)
	private String name;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Skill other = (Skill) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}