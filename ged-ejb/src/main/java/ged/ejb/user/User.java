package ged.ejb.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.Subject;
import ged.ejb.core.file.File;
import ged.ejb.core.model.AbstractIndexedEntity;
import ged.ejb.user.role.Role;

@Entity
@Indexed
public class User extends AbstractIndexedEntity implements Subject  {

	private static final long serialVersionUID = 1L;

	@Field(name = "dateOfJoin", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "dateOfJoin")
	private LocalDate dateOfJoin;
	
	@Email
	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	protected String email;

	@NotNull
	@Column(nullable = false)
	private String language;

	@Field(name = "lastConnection", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "lastConnection")
	private LocalDateTime lastConnection;

	@ManyToOne
	@JoinColumn(name = "managerId")
	@IndexedEmbedded(depth = 1)
	private User manager;

	@NotNull
	@Column(nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	protected String name;

	@Column(length = 12)
	protected String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "picture")
	protected File picture;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Field(name = "role", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "role")
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage = 10;

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
		final User other = (User) obj;
		return Objects.equals(other.email, this.email);
	}

	public LocalDate getDateOfJoin() {
		return this.dateOfJoin;
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

	public LocalDateTime getLastConnection() {
		return this.lastConnection;
	}

	public User getManager() {
		return this.manager;
	}

	public String getName() {
		return this.name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public File getPicture() {
		return this.picture;
	}

	public Role getRole() {
		return this.role;
	}

	public Integer getRowsPerPage() {
		return this.rowsPerPage;
	}

	public String getSurname() {
		return this.surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email);
	}

	public boolean hasRole(final Role role) {
		Objects.requireNonNull(role);
		return role.equals(this.role);
	}

	public boolean isAdmin() {
		if (this.role == null) {
			return false;
		}
		return this.hasRole(Role.ADMIN);
	}

	public boolean isManaged() {
		return this.manager != null;
	}

	public void setDateOfJoin(final LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setLastConnection(final LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPicture(final File picture) {
		this.picture = picture;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setRowsPerPage(final Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return this.email;
	}
}
