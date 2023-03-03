package es.nivel36.laie.ejb.curriculum;

import java.util.Objects;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "SKILL")
public class Skill extends AbstractEntity implements Comparable<Skill> {

	private static final long serialVersionUID = 4288106580541111562L;

	@ManyToOne
	@JoinColumn(name = "CURRICULUM_ID")
	private Curriculum curriculum;

	@FullTextField
	@NotNull
	@Column(name = "NAME", nullable = false)
	private String name;

	public Skill() {
	}

	public Skill(final String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Skill skill) {
		return this.name.compareTo(skill.name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final Skill other = (Skill) obj;
		return Objects.equals(other.name, this.name);
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	public void setCurriculum(Curriculum curriculum) {
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