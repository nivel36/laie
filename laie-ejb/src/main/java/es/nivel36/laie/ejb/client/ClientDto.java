package es.nivel36.laie.ejb.client;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.hibernate.search.annotations.Indexed;

import es.nivel36.laie.ejb.AddressDto;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.user.SimpleUserDto;

@Indexed
public class ClientDto implements Serializable {

	private static final long serialVersionUID = 8944234128171451526L;

	private AddressDto address;

	private String cif;

	private List<ContactDto> contacts;

	private boolean deleted;

	private List<JobOfferDto> jobOffers	;

	private String name;

	private SimpleUserDto owner;

	private String phoneNumber;

	private String uid;

	public AddressDto getAddress() {
		if (this.address == null) {
			this.address = new AddressDto();
		}
		return this.address;
	}

	public String getCif() {
		return this.cif;
	}

	public List<ContactDto> getContacts() {
		return this.contacts;
	}

	public List<JobOfferDto> getJobOffers() {
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

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setAddress(final AddressDto address) {
		this.address = address;
	}

	public void setCif(final String cif) {
		this.cif = cif;
	}

	public void setContacts(final List<ContactDto> contacts) {
		this.contacts = contacts;
	}

	void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	public void setJobOffers(final List<JobOfferDto> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setName(final String name) {
		this.name = name;
	}

	void setOwner(final SimpleUserDto owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	void setUid(String uid) {
		this.uid = uid;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
