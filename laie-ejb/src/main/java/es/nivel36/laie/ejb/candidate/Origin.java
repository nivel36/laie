package es.nivel36.laie.ejb.candidate;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.nivel36.laie.ejb.core.model.AbstractEntity;

@Table(name = "ORIGIN")
@Entity
public class Origin extends AbstractEntity {

	private static final long serialVersionUID = 4930787003402151549L;

	@Column(name = "CODE", length = 32)
	private String code;

	@Column(name = "OTHER", length = 64)
	private String other;

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
		final Origin otherOrigin = (Origin) obj;
		return Objects.equals(otherOrigin.code, this.code) && Objects.equals(otherOrigin.other, this.other);
	}

	public String getCode() {
		return this.code;
	}

	public String getOther() {
		return this.other;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.code, this.other);
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setOther(final String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		if (this.other != null) {
			return this.other;
		} else {
			return this.code;
		}
	}
}
