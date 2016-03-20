package ged.ejb.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Action;

@Entity
@Indexed
@AnalyzerDef(name = "customanalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class) })

public class User extends AbstractEntity {

	private static final long serialVersionUID = 5920907439877095636L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Action> actions = new ArrayList<Action>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	@Field
	private String email;

	@Transient
	private String fullName;

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
	private String password;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage;

	@NotNull
	@Column(length = 64, nullable = false)
	@Field
	private String surename;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "manager", orphanRemoval = false)
	private List<User> team;

	@NotNull
	@Column(length = 16, nullable = false, unique = true)
	@Field
	private String username;

	public void addAction(final Action action) {
		if (action == null) {
			throw new NullPointerException();
		}
		this.actions.add(action);
	}

	public void addBookmark(final Bookmark bookmark) {
		if (bookmark == null) {
			throw new NullPointerException();
		}
		this.bookmarks.add(bookmark);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if ((this.username == null) && (other.username != null)) {
			return false;
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
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

	public List<User> getTeam() {
		return this.team;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.username == null) ? 0 : this.username.hashCode());
		return result;
	}

	public boolean hasRole(final String roleName) {
		return this.role.getName().equals(roleName);
	}

	public void removeAction(final Action action) {
		if (action == null) {
			throw new NullPointerException();
		}
		this.actions.remove(action);
	}

	public void removeBookmark(final Bookmark bookmark) {
		if (bookmark == null) {
			throw new NullPointerException();
		}
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

	public void setTeam(final List<User> team) {
		this.team = team;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return this.name + " " + this.surename;
	}
}
