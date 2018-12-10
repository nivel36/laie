package ged.api.v1.client;

import java.util.Set;

import javax.validation.constraints.Pattern;

import ged.api.v1.Dto;
import ged.ejb.client.Contact;
import ged.ejb.job.offer.JobOffer;

public class ClientDto implements Dto {

	private String cif;

	private String city;

	private Set<Contact> contacts;

	private Set<JobOffer> jobOffers;

	private String name;

	private String owner;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
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
