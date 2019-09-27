package ged.ejb.core.tag;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.model.AbstractEntity;

@Entity
@Indexed
public class Tag extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(length = 128, nullable = false)
	@Field
	private String label;

	public Tag() {
	}

	public Tag(final String label) {
		Objects.requireNonNull(label, "Label can't be null");
		this.label = label;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Tag other = (Tag) obj;
		return Objects.equals(this.label, other.label);
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.label);
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.label;
	}
}