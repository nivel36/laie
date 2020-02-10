package ged.ejb.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.model.Obfuscable;
import ged.ejb.person.Person;

@Entity
@Indexed
public class Contact extends Person implements Obfuscable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "clientId", nullable = false)
	private Client client;

	@Column(length = 2)
	private String language;

	@Column(length = 128)
	private String position;

	@NotNull
	@Column(unique = true, nullable = false)
	private String uid;

	public Client getClient() {
		return this.client;
	}

	public String getLanguage() {
		return this.language;
	}

	public String getPosition() {
		return this.position;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	@Override
	public void setUid(final String uid) {
		this.uid = uid;
	}
}