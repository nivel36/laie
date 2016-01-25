package ged.ejb.service.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AuditedEntity;

@Entity
public class PerfilProfesional extends AuditedEntity {

	private static final long serialVersionUID = 8232686306392236943L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curriculumId", nullable = false, updatable = false)
	private Curriculum curriculum;

	@NotNull
	@Column(length = 128, nullable = false)
	private String name;

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public String getName() {
		return name;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilProfesional other = (PerfilProfesional) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
