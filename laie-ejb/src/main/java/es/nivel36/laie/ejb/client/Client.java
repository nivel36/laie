package es.nivel36.laie.ejb.client;

import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.core.model.Erasable;
import es.nivel36.laie.ejb.core.model.Ownerable;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Indexed
@Entity
@Table(name = "CLIENT", indexes = {
		@Index(name = "UX_CLIENT_CIF", columnList = "CIF", unique = true) }, uniqueConstraints = {
				@UniqueConstraint(name = "UQ_CLIENT_CIF", columnNames = { "CIF" }),
				@UniqueConstraint(name = "UQ_CLIENT_NAME", columnNames = { "NAME" }) })
public class Client extends AbstractEntity implements Ownerable, Erasable, Auditable {

	private static final long serialVersionUID = 3562472646025185677L;

	@Embedded
	@IndexedEmbedded
	private Address address;

	@Column(name = "CIF", length = 16)
	@FullTextField(name = "_cif")
	private String cif;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<Contact> contacts;

	@Column(name = "DELETED", nullable = false)
	@FullTextField
	private boolean deleted;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
	private Set<JobOffer> jobOffers;

	@NotNull
	@Column(name = "NAME", unique = true, nullable = false)
	@FullTextField(name = "_name")
	@KeywordField(name = "name",  sortable = Sortable.YES)
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
