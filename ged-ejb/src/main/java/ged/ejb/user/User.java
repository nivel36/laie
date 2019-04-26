package ged.ejb.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.user.role.Role;

@Entity
@Indexed
public class User extends AbstractAuditedEntity {

	private static final long serialVersionUID = 5920907439877095636L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private Set<Bookmark> bookmarks = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
	@JoinColumn(name = "credentialId", unique = true, nullable = false, updatable = false)
	@NotNull
	private Credential credential;

	private LocalDate dateOfJoin;

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	private String email;

	@Column(length = 128, nullable = true, unique = true)
	private String imageFileName;

	@NotNull
	@Column(length = 2, nullable = false)
	private String language;

	private LocalDateTime lastConnection;

	@ManyToOne
	@JoinColumn(name = "managerId", nullable = true)
	private User manager;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String name;

	@Pattern(regexp = "(?:[+]?(?:[0-9]{1,5}|\\x28[0-9]{1,5}\\x29)[ ]?)?[0-9]{2}(?:[0-9][ ]?){6}[0-9]")
	@Column(length = 12)
	private String phoneNumber;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage = 10;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String surname;

	public void addBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.bookmarks.add(bookmark);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		return Objects.equals(this.email, other.email);
	}

	public Set<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	public Credential getCredential() {
		return this.credential;
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

	public String getImageFileName() {
		return this.imageFileName;
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

	public void newCredential(final String password) {
		this.credential = new Credential(password);
	}

	public void removeBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.bookmarks.remove(bookmark);
	}

	public void setBookmarks(final Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setCredential(final Credential credential) {
		this.credential = credential;
	}

	public void setDateOfJoin(final LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
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
		return this.getFullName();
	}
}
