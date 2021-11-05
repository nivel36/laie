package es.nivel36.laie.ejb.client;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.search.annotations.Indexed;

import es.nivel36.laie.ejb.core.model.AddressDto;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.SimpleUserDto;

@Entity
@Indexed
public class ClientDto implements Serializable {

	private static final long serialVersionUID = 8944234128171451526L;

	private AddressDto address;

	private String cif;

	private Set<ContactDto> contacts = new HashSet<>();

	private boolean deleted;

	private Set<JobOffer> jobOffers = new HashSet<>();

	private String name;

	private SimpleUserDto owner;

	private String phoneNumber;

	private String uid;

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
		final ClientDto other = (ClientDto) obj;
		return Objects.equals(this.name, other.name);
	}

	public AddressDto getAddress() {
		if (this.address != null) {
			return this.address;
		} else {
			return new AddressDto();
		}
	}

	public String getCif() {
		return this.cif;
	}

	public Set<ContactDto> getContacts() {
		return this.contacts;
	}

	public Set<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getName() {
		return this.name;
	}

	public SimpleUserDto getOwner() {
		return this.owner;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setAddress(final AddressDto address) {
		this.address = address;
	}

	public void setCif(final String cif) {
		this.cif = cif;
	}

	public void setContacts(final Set<ContactDto> contacts) {
		this.contacts = contacts;
	}

	void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	public void setJobOffers(final Set<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwner(final SimpleUserDto owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
