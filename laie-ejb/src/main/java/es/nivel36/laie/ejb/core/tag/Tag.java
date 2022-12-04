package es.nivel36.laie.ejb.core.tag;

import java.util.Objects;

import org.bouncycastle.util.Store;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(indexes = { @javax.persistence.Index(name = "UX_TAG_LABEL", columnList = "label", unique = true) })
public class Tag extends AbstractEntity {

	private static final long serialVersionUID = -2070061878397221965L;

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@FullTextField(name = "_label")
	@FullTextField(name = "label", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "label")
	private String label;

	public Tag() {
	}

	public Tag(final String label) {
		Objects.requireNonNull(label);
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(final String label) {
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

	@Override
	public int hashCode() {
		return Objects.hash(this.label);
	}

	@Override
	public String toString() {
		return this.label;
	}
}