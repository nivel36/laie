package ged.ejb.client;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.Address;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.offer.JobOffer;

@Entity
@Indexed
public class Client extends AbstractAuditedEntity {

	private static final long serialVersionUID = -5319357138994738654L;

	@Embedded
	private Address address;

	@Column(length = 10, unique = true, nullable = true)
	private String cif;

	@ContainedIn
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private List<JobOffer> jobOffers;

	@Field
	@Column(length = 128, unique = true, nullable = false)
	@NotNull
	private String name;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Client other = (Client) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public Address getAddress() {
		return this.address;
	}

	public String getCif() {
		return this.cif;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setCif(final String cif) {
		this.cif = cif;
	}

	public void setJobOffers(final List<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
