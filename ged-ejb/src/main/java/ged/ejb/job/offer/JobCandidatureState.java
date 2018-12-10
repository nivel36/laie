package ged.ejb.job.offer;

import javax.persistence.Entity;
import javax.persistence.Table;

import ged.ejb.core.maintenance.AbstractEnumEntity;

@Entity
@Table(name = "JOB_CANDIDATURE_STATE")
public class JobCandidatureState extends AbstractEnumEntity {

	private static final long serialVersionUID = -4388524806296516306L;

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
		return super.equals(obj);
	}

	public String getColor() {
		return this.color;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
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
}