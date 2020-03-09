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
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.person.Person;
import ged.ejb.user.role.Role;

@Entity
@Indexed
public class User extends Person  {

	private static final long serialVersionUID = 1L;

	@Field(name = "dateOfJoin", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "dateOfJoin")
	private LocalDate dateOfJoin;

	@NotNull
	@Column(nullable = false)
	private String language;

	@Field(name = "lastConnection", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "lastConnection")
	private LocalDateTime lastConnection;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return super.equals(other);
	}

	@ManyToOne
	@JoinColumn(name = "managerId")
	@IndexedEmbedded(depth = 1)
	private User manager;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Field(name = "role", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "role")
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage = 10;

	public LocalDate getDateOfJoin() {
		return this.dateOfJoin;
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

	public Role getRole() {
		return this.role;
	}

	public Integer getRowsPerPage() {
		return this.rowsPerPage;
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

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setLastConnection(final LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setRowsPerPage(final Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	@Override
	public String toString() {
		return this.email;
	}
}
