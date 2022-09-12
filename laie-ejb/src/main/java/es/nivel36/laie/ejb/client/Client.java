package es.nivel36.laie.ejb.client;

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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.Erasable;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

@Indexed
@Entity
@Table(name = "CLIENT", indexes = {
		@javax.persistence.Index(name = "UX_CLIENT_CIF", columnList = "CIF", unique = true) }, uniqueConstraints = {
				@UniqueConstraint(name = "UQ_CLIENT_CIF", columnNames = { "CIF" }),
				@UniqueConstraint(name = "UQ_CLIENT_NAME", columnNames = { "NAME" }) })
public class Client extends AbstractEntity implements Ownerable, Erasable, Auditable {

	private static final long serialVersionUID = 3562472646025185677L;

	@Embedded
	@IndexedEmbedded
	private Address address;

	@Column(name = "CIF", length = 16)
	@Field(name = "_cif")
	private String cif;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<Contact> contacts;

	@Column(name = "DELETED", nullable = false)
	@Field
	private boolean deleted;

	@ContainedIn
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<JobOffer> jobOffers;

	@NotNull
	@Column(name = "NAME", unique = true, nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "OWNER_ID", nullable = false)
	@IndexedEmbedded
	private User owner;

	@Column(name = "PHONE_NUMBER", length = 16)
	private String phoneNumber;

	public Address getAddress() {
		if (this.address == null) {
			this.address = new Address();
		}
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

	@Override
	public User getOwner() {
		return this.owner;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
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

	@Override
	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	public void setJobOffers(final Set<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Client other = (Client) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public int hashCode() {
		return 31 * Objects.hash(this.name);
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getEntityTitle() {
		return this.name;
	}

	@Override
	public String getEntityName() {
		return "CLIENT";
	}
}
