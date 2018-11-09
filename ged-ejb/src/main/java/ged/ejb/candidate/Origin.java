package ged.ejb.candidate;

import java.util.Objects;

import javax.persistence.Entity;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Origin extends AbstractEntity {

	private static final long serialVersionUID = -8905604950314447602L;

	private String code;

	private String other;

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
		final Origin other = (Origin) obj;
		return Objects.equals(other.code, this.code) && Objects.equals(other.other, this.other);
	}

	public String getCode() {
		return code;
	}

	public String getOther() {
		return other;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, other);
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setOther(final String other) {
		this.other = other;
	}
}
