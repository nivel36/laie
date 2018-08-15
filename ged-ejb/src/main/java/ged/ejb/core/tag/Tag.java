package ged.ejb.core.tag;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractEntity;

@Entity
@Indexed
public class Tag extends AbstractEntity {

	private static final long serialVersionUID = -2676859619371128798L;

	@ManyToMany
	@JoinTable(name = "candidate_tag", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "candidate_id"))
	private Set<Candidate> candidates;

	@NotNull
	@Column(length = 128, nullable = false)
	@Field
	private String label;

	
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Tag other = (Tag) obj;
		return Objects.equals(this.label, other.label);
	}

	public Set<Candidate> getCandidates() {
		return this.candidates;
	}

	public String getLabel() {
		return this.label;
	}

	
	public int hashCode() {
		return Objects.hash(this.label);
	}

	public void setCandidates(final Set<Candidate> candidates) {
		this.candidates = candidates;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	
	public String toString() {
		return this.label;
	}
}