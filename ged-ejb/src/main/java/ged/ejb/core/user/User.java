package ged.ejb.core.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Action;

@Entity
public class User extends AbstractEntity {

	private static final long serialVersionUID = 5920907439877095636L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Action> actions = new ArrayList<Action>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();

	@NotNull
	@Column(length = 128, nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(length = 2, nullable = false)
	private String language;

	@ManyToOne
	@JoinColumn(name = "managerId", nullable = true, updatable = false)
	private User manager;

	@NotNull
	@Column(length = 64, nullable = false)
	private String name;

	@NotNull
	private String password;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false, updatable = false)
	private Role role;

	@NotNull
	@Column(nullable = false)
	private Integer rowsPerPage;

	@NotNull
	@Column(length = 64, nullable = false)
	private String surename;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "manager", orphanRemoval = false)
	private List<User> team;

	@NotNull
	@Column(length = 12, nullable = false, unique = true)
	private String username;

	public void addAction(Action action) {
		if (action == null) {
			throw new NullPointerException();
		}
		actions.add(action);
	}

	public void addBookmark(Bookmark bookmark) {
		if (bookmark == null) {
			throw new NullPointerException();
		}
		bookmarks.add(bookmark);
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
		if (username == null && other.username != null) {
			return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public List<Action> getActions() {
		return actions;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public String getEmail() {
		return email;
	}

	public String getLanguage() {
		return language;
	}

	public User getManager() {
		return manager;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public String getSurename() {
		return surename;
	}

	public List<User> getTeam() {
		return team;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public void removeAction(Action action) {
		if (action == null) {
			throw new NullPointerException();
		}
		actions.remove(action);
	}

	public void removeBookmark(Bookmark bookmark) {
		if (bookmark == null) {
			throw new NullPointerException();
		}
		bookmarks.remove(bookmark);
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setSurename(String surename) {
		this.surename = surename;
	}

	public void setTeam(List<User> team) {
		this.team = team;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", language=" + language + ", name=" + name + ", surename=" + surename
				+ ", username=" + username + "]";
	}
}
