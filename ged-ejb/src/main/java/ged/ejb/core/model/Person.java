package ged.ejb.core.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Person extends AbstractEntity {

	private static final long serialVersionUID = 1256626229385088883L;

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	protected String email;

	@Column(unique = true)
	protected String imageFileName;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	protected String name;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12)
	protected String phoneNumber;

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
		final Person other = (Person) obj;
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

	public String getImageFileName() {
		return this.imageFileName;
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getSurname() {
		return this.surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email);
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}

}
