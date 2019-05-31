package ged.ejb.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ged.ejb.core.model.Person;

@Entity
public class Contact extends Person {

	private static final long serialVersionUID = -5910261570364209778L;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = false)
	private Client client;

	@Column(length = 2)
	private String language;

	@Column(length = 128)
	private String position;

	public Client getClient() {
		return client;
	}

	public String getLanguage() {
		return language;
	}

	public String getPosition() {
		return position;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}