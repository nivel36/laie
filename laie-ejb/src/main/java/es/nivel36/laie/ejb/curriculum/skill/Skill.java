package es.nivel36.laie.ejb.curriculum.skill;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Entity
@Indexed
public class Skill extends AbstractEntity implements Comparable<Skill> {

	private static final long serialVersionUID = 4288106580541111562L;
	
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

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Skill skill) {
		return this.compareTo(skill);
	}
}