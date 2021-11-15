package es.nivel36.laie.ejb.job.candidature;

import java.io.Serializable;
import java.util.Objects;

public class JobCandidatureStateDto implements Serializable {

	private static final long serialVersionUID = 6059181115990961833L;

	private boolean approved;

	private boolean declined;

	private boolean first;

	private String name;

	public boolean isApproved() {
		return approved;
	}

	void setApproved(final boolean approved) {
		this.approved = approved;
	}

	public boolean isDeclined() {
		return declined;
	}

	void setDeclined(final boolean declined) {
		this.declined = declined;
	}

	public boolean isFirst() {
		return first;
	}

	void setFirst(final boolean first) {
		this.first = first;
	}

	public String getName() {
		return name;
	}

	void setName(final String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		JobCandidatureStateDto other = (JobCandidatureStateDto) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}
}
