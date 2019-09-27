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

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false)
	private Curriculum curriculum;

	@Field
	@NotNull
	@Column(nullable = false)
	private String name;

	public Skill() {
	}

	public Skill(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Skill other = (Skill) obj;
		return Objects.equals(other.name, this.name);
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
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