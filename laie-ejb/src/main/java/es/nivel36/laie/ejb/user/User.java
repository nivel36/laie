package es.nivel36.laie.ejb.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.model.AbstractObfuscableEntity;
import es.nivel36.laie.ejb.core.model.Indexable;

@Entity
@Indexed
public class User extends AbstractObfuscableEntity implements Indexable {

	private static final long serialVersionUID = -3719561601581901723L;

	@Field(name = "dateOfJoin", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "dateOfJoin")
	private LocalDate dateOfJoin;

	@Field(name = "_email")
	@Column(length = 128, nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String language;

	@Field(name = "lastConnection", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "lastConnection")
	private LocalDateTime lastConnection;

	@ManyToOne
	@JoinColumn(name = "managerId")
	@IndexedEmbedded(depth = 1)
	private User manager;

	@Column(nullable = false)
	@Field(name = "_name")
	@Field(name = "name", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "name")
	private String name;

	@Column(length = 12)
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "picture")
	private File picture;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Field(name = "role", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "role")
	private Role role;

	@Column(nullable = false)
	private Integer rowsPerPage = 10;

	@Column(nullable = false)
	@Field(name = "_surname")
	@Field(name = "surname", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "surname")
	private String surname;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Bookmark> bookmarks;

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

	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(this.email);
	}

	@Override
	public String toString() {
		return this.email;
	}
}
