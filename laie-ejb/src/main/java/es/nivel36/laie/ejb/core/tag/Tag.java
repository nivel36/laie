package es.nivel36.laie.ejb.core.tag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Indexed
@Table(indexes = { @Index(name = "UX_TAG_LABEL", columnList = "label", unique = true) })
public class Tag extends AbstractEntity {

	private static final long serialVersionUID = -2070061878397221965L;

	@ManyToMany(mappedBy = "tags")
	@IndexedEmbedded(includeDepth = 1)
	private Set<Candidate> candidates = new HashSet<>();

	@NotNull
	@Column(nullable = false, unique = true, columnDefinition = "TEXT")
	@FullTextField(name = "_label")
	@KeywordField(name = "label", sortable = Sortable.YES)
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
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final Tag other = (Tag) obj;
		return Objects.equals(this.label, other.label);
	}

	public Set<Candidate> getCandidates() {
		return candidates;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.label);
	}

	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.label;
	}
}