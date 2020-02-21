package ged.ejb.core.tag;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.model.AbstractIndexedEntity;

@Entity
@Indexed
public class Tag extends AbstractIndexedEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(length = 128, nullable = false)
	@Field(name = "_label")
	@Field(name = "label", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "label")
	private String label;

	public Tag() {
	}

	public Tag(final String label) {
		Objects.requireNonNull(label);
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