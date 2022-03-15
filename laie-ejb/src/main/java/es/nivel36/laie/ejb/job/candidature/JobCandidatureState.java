package es.nivel36.laie.ejb.job.candidature;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.FacetEncodingType;
import org.hibernate.search.annotations.Field;

import es.nivel36.laie.ejb.core.EventState;
import es.nivel36.laie.ejb.core.model.AbstractIndexableEntity;

@Entity
@Table(name = "JOB_CANDIDATURE_STATE")
public class JobCandidatureState extends AbstractIndexableEntity implements EventState {

	private static final long serialVersionUID = -1530029544557152044L;

	private boolean approved;

	private boolean declined;

	private boolean first;

	@Field(analyze = Analyze.NO)
	@Facet(encoding = FacetEncodingType.STRING)
	@Column(unique = true)
	private String name;

	@Override
	public String getName() {
		return this.name;
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
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	@Override
	public String toString() {
		return this.name;
	}
}