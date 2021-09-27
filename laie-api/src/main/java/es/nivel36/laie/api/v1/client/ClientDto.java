package es.nivel36.laie.api.v1.client;

import java.util.Set;

import es.nivel36.laie.api.v1.Dto;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.job.offer.JobOffer;

public class ClientDto implements Dto {

	private String cif;

	private String city;

	private Set<Contact> contacts;

	private Set<JobOffer> jobOffers;

	private String name;

	private String owner;

	private String phoneNumber;

	private String state;

	public String getCif() {
		return this.cif;
	}

	public String getCity() {
		return this.city;
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

	public String getOwner() {
		return this.owner;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getState() {
		return this.state;
	}

	public void setCif(final String cif) {
		this.cif = cif;
	}

	public void setCity(final String city) {
		this.city = city;
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

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setState(final String state) {
		this.state = state;
	}

}
