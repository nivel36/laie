package es.nivel36.laie.ejb.client;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.core.model.AbstractIndexedEntity;

@Entity
@Indexed
public class Contact extends AbstractIndexedEntity {

	private static final long serialVersionUID = -2403549918176092142L;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "clientId", nullable = false)
	private Client client;

	@Email
	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field(name = "_email")
	@Field(name = "email", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	protected String email;

	@Column(length = 2)
	private String language;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	protected String name;

	@Column(length = 12)
	protected String phoneNumber;

	@Column(length = 128)
	private String position;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_surname")
	@Field(name = "surname", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "surname")
	protected String surname;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Contact other = (Contact) obj;
		return Objects.equals(other.email, this.email);
	}

	public Client getClient() {
		return this.client;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFullName() {
		if (this.name == null) {
			return null;
		}
		return new StringBuilder(this.name).append(" ").append(this.surname).toString();
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

	public String getPosition() {
		return this.position;
	}

	public String getSurname() {
		return this.surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email);
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

	public void setPosition(final String position) {
		this.position = position;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}