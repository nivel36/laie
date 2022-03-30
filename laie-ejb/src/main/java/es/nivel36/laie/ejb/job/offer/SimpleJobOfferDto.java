package es.nivel36.laie.ejb.job.offer;

import java.io.Serializable;
import java.util.Objects;

import es.nivel36.laie.ejb.AddressDto;
import es.nivel36.laie.ejb.client.SimpleClientDto;

public class SimpleJobOfferDto implements Serializable {

	private static final long serialVersionUID = 8242562872236511063L;

	private AddressDto address;
	
	private SimpleClientDto client;

	private JobOfferState state;
	
	private String title;
	
	private String uid;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleJobOfferDto other = (SimpleJobOfferDto) obj;
		return state == other.state && Objects.equals(title, other.title);
	}

	public AddressDto getAddress() {
		return address;
	}

	public SimpleClientDto getClient() {
		return client;
	}

	public JobOfferState getState() {
		return state;
	}

	public String getTitle() {
		return title;
	}

	public String getUid() {
		return uid;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(state, title);
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public void setClient(SimpleClientDto client) {
		this.client = client;
	}
	
	public void setState(JobOfferState state) {
		this.state = state;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return title;
	}
}
