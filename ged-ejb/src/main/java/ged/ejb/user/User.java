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

import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Person;
import ged.ejb.user.role.Role;

@Entity
@Indexed
public class User extends Person {

	private static final long serialVersionUID = 5920907439877095636L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private Set<Bookmark> bookmarks = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
	@JoinColumn(name = "credentialId", unique = true, nullable = false, updatable = false)
	@NotNull
	private Credential credential;

	private LocalDate dateOfJoin;

	@NotNull
	@Column(nullable = false)
	private String language;

	private LocalDateTime lastConnection;

	@ManyToOne
	@JoinColumn(name = "managerId")
	private User manager;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage = 10;

	public void addBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.bookmarks.add(bookmark);
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
}
