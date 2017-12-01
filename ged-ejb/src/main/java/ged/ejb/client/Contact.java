package ged.ejb.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;

import ged.ejb.core.model.AbstractAuditedEntity;

@Entity
public class Contact extends AbstractAuditedEntity {

	private static final long serialVersionUID = -5910261570364209778L;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = true)
	private Client client;

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	private String email;

	@NotNull
	@Column(length = 2, nullable = false)
	private String language;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String name;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12)
	private String phoneNumber;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String surename;

	public Client getClient() {
		return this.client;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFullName() {
		return this.name + " " + this.surename;
	}

	public String getLanguage() {
		return this.language;
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getSurename() {
		return this.surename;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	@Override
	public String toString() {
		return getFullName();
	}
}