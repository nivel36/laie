package ged.ejb.job.candidature;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;

@Entity
@Table(name = "JOB_CANDIDATURE_STATE")
public class JobCandidatureState extends AbstractEntity {

	private static final long serialVersionUID = -4388524806296516306L;

	@I18n
	private String name;

	private String color;

	private boolean first;

	private boolean last;

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
		final JobCandidatureState other = (JobCandidatureState) obj;
		return Objects.equals(this.name, other.name);
	}

	public String getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	public boolean isFirst() {
		return this.first;
	}

	public boolean isLast() {
		return this.last;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public void setFirst(final boolean first) {
		this.first = first;
	}

	public void setLast(final boolean last) {
		this.last = last;
	}

	public void setName(final String name) {
		this.name = name;
	}
}