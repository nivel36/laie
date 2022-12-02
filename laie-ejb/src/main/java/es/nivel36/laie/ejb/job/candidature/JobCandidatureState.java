package es.nivel36.laie.ejb.job.candidature;

import java.util.Objects;
import java.util.Set;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.FacetEncodingType;
import org.hibernate.search.annotations.Field;

import es.nivel36.laie.ejb.core.EventState;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOB_CANDIDATURE_STATE")
public class JobCandidatureState extends AbstractEntity implements EventState {

	private static final long serialVersionUID = -1530029544557152044L;

	@Column(name = "APPROVED")
	private boolean approved;

	@Column(name = "DECLINED")
	private boolean declined;

	@Column(name = "FIRST")
	private boolean first;

	@FullTextField(analyze = Analyze.NO)
	@Facet(encoding = FacetEncodingType.STRING)
	@Column(name = "NAME", unique = true)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "JOB_CANDIDATURE_STATE_REL", joinColumns = {
			@JoinColumn(name = "PARENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "JOB_CANDIDATURE_ID") })
	private Set<JobCandidatureState> nextStates;

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

	@Override
	public String getName() {
		return this.name;
	}

	public Set<JobCandidatureState> getNextStates() {
		return nextStates;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	public boolean isApproved() {
		return this.approved;
	}

	public boolean isClosed() {
		return this.isApproved() || this.isDeclined();
	}

	public boolean isDeclined() {
		return this.declined;
	}

	public boolean isFirst() {
		return this.first;
	}

	public void setApproved(final boolean approved) {
		this.approved = approved;
	}

	public void setDeclined(final boolean declined) {
		this.declined = declined;
	}

	public void setFirst(final boolean first) {
		this.first = first;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setNextStates(Set<JobCandidatureState> nextStates) {
		this.nextStates = nextStates;
	}

	@Override
	public String toString() {
		return this.name;
	}
}