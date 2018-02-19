package ged.ejb.client;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.Address;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Entity
@Indexed
public class Client extends AbstractAuditedEntity {

	private static final long serialVersionUID = -5319357138994738654L;

	@Embedded
	private Address address;

	@Column(length = 10, unique = true, nullable = true)
	private String cif;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<Contact> contacts;

	@ContainedIn
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private List<JobOffer> jobOffers;

	@Field
	@Column(length = 128, unique = true, nullable = false)
	@NotNull
	private String name;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ownerId", nullable = false)
	private User owner;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12)
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
		if (getClass() != obj.getClass()) {
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

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getName() {
		return this.name;
	}

	public User getOwner() {
		return this.owner;
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

	public void setJobOffers(final List<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
