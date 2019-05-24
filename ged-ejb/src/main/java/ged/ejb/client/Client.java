package ged.ejb.client;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.Address;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.offer.JobOffer;

@Entity
@Indexed
public class Client extends AbstractAuditedEntity {

	private static final long serialVersionUID = -5319357138994738654L;

	@Embedded
	@IndexedEmbedded
	private Address address;

	@Column(unique = true)
	private String cif;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<Contact> contacts;

	@ContainedIn
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<JobOffer> jobOffers;

	@NotNull
	@Column(unique = true, nullable = false)
	@Fields({ @Field(name = "_name"), @Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO) })
	@SortableField(forField = "name")
	private String name;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	private String phoneNumber;

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
		final Client other = (Client) obj;
		return Objects.equals(this.name, other.name);
	}

	public Address getAddress() {
		return this.address;
	}

	public String getCif() {
		return this.cif;
	}

	public Set<Contact> getContacts() {
		return this.contacts;
	}

	public Set<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public void setCif(final String cif) {
		this.cif = cif;
	}

	public void setContacts(final Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setJobOffers(final Set<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
