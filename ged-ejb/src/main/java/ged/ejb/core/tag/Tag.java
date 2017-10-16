package ged.ejb.core.tag;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import ged.ejb.core.i18n.I18n;
import ged.ejb.core.model.AbstractEntity;

@Entity
public class Tag extends AbstractEntity {

	private static final long serialVersionUID = -2676859619371128798L;

	@NotNull
	@Column(length = 128, nullable = false)
	@I18n
	private String label;

	public String getLabel() {
		return this.label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		return Objects.hash(label);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tag other = (Tag) obj;
		return Objects.equals(this.label, other.label);
	}

	@Override
	public String toString() {
		return label;
	}
}