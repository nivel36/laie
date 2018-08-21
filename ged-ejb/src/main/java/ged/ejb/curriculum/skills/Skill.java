package ged.ejb.curriculum.skills;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.curriculum.Curriculum;

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
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Skill other = (Skill) obj;
		return Objects.equals(this.name, other.name);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getName() {
		return this.name;
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