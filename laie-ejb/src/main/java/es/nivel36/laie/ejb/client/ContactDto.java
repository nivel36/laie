package es.nivel36.laie.ejb.client;

import java.io.Serializable;
import java.util.Objects;

public class ContactDto implements Serializable {

	private static final long serialVersionUID = 310171684413609954L;

	private String email;

	private String language;

	private String name;

	private String phoneNumber;

	private String position;

	private String surname;

	private String uid;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final Contact other = (Contact) obj;
		return Objects.equals(other.email, this.email);
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

	public String getUid() {
		return this.uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email);
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

	void setUid(final String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}
