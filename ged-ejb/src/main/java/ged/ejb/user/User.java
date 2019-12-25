package ged.ejb.user;

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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import ged.ejb.core.model.Obfuscable;
import ged.ejb.core.security.LoginToken;
import ged.ejb.person.Person;
import ged.ejb.user.role.Role;

@Entity
@Indexed
public class User extends Person implements Obfuscable {

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
	@JoinColumn(name = "credentialId", unique = true, nullable = false, updatable = false)
	@NotNull
	private Credential credential;

	@Field(name = "dateOfJoin", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "dateOfJoin")
	private LocalDate dateOfJoin;

	@NotNull
	@Column(nullable = false)
	private String language;

	@Field(name = "lastConnection", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
	@SortableField(forField = "lastConnection")
	private LocalDateTime lastConnection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private Set<LoginToken> loginTokens;

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

	@NotNull
	@Column(unique = true, nullable = false)
	private String uid;

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

	public Set<LoginToken> getLoginTokens() {
		return this.loginTokens;
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

	@Override
	public String getUid() {
		return this.uid;
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

	public void setLoginTokens(final Set<LoginToken> loginTokens) {
		this.loginTokens = loginTokens;
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

	public void setUid(final String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.email;
	}
}
