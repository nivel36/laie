package ged.ejb.user;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import ged.ejb.core.action.Action;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.role.Role;

@Entity
@Indexed
public class User extends AbstractAuditedEntity {

	private static final long serialVersionUID = 5920907439877095636L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Action> actions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Bookmark> bookmarks;

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	private String email;

	@Transient
	private String fullName;

	@Column(length = 128, nullable = true, unique = true)
	private String imageFileName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", orphanRemoval = false)
	private List<JobOffer> jobOffers;

	@NotNull
	@Column(length = 2, nullable = false)
	private String language;

	@ManyToOne
	@JoinColumn(name = "managerId", nullable = true)
	private User manager;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String name;

	@NotNull
	@Column(length = 64, nullable = false)
	private String password;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId", nullable = false)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String surename;

	@NotNull
	@Column(length = 16, nullable = false, unique = true)
	@Field
	private String username;

	public void addAction(final Action action) {
		Objects.requireNonNull(action);
		this.actions.add(action);
	}

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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		return Objects.equals(this.username, other.username);
	}

	public List<Action> getActions() {
		return this.actions;
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFullName() {
		return this.name + " " + this.surename;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getLanguage() {
		return this.language;
	}

	public User getManager() {
		return this.manager;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public Role getRole() {
		return this.role;
	}

	public int getRowsPerPage() {
		return this.rowsPerPage;
	}

	public String getSurename() {
		return this.surename;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.username);
	}

	public boolean hasRole(final String roleName) {
		return this.role.getName().equals(roleName);
	}

	public boolean isAdmin() {
		return "ADMIN".equals(this.role.getName());
	}

	public boolean isRecruiter() {
		return "RECRUITER".equals(this.role.getName());
	}

	public boolean isRecruiterAdmin() {
		return "RECRUITER_ADMIN".equals(this.role.getName());
	}

	public void removeAction(final Action action) {
		Objects.requireNonNull(action);
		this.actions.remove(action);
	}

	public void removeBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.bookmarks.remove(bookmark);
	}

	public void setActions(final List<Action> actions) {
		this.actions = actions;
	}

	public void setBookmarks(final List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public void setImageFileName(final String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setJobOffers(final List<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setRowsPerPage(final int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return this.name + " " + this.surename;
	}
}
