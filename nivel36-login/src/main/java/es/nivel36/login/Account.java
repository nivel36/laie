package es.nivel36.login;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account implements Serializable {
	
	private static final Random RANDOM = new SecureRandom();
	
	private static final long serialVersionUID = -7213946632987932359L;

	private LocalDate created;

	private LocalDate expired;

	@Column(nullable = false)
	private byte[] hashPassword;

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy = "account", orphanRemoval = true)
	private Set<LoginToken> loginTokens;

	private String role;

	@Column(nullable = false)
	private byte[] salt;
	
	private String username;

	public Account() {
	}

	public Account(final String username, final String password) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(username);
		this.hashPassword = new byte[32];
		this.salt = new byte[16];
		this.created = LocalDate.now();
		this.username = username;
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	private byte[] buildHashPassword(final String password) {
		return CriptoUtil.digestPassword(password, this.salt);
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
		final Account other = (Account) obj;
		return Objects.equals(this.hashPassword, other.hashPassword) && Objects.equals(this.salt, other.salt);
	}

	public void expire() {
		this.expired = LocalDate.now();
	}

	public LocalDate getCreated() {
		return this.created;
	}

	public Long getId() {
		return id;
	}

	public Set<LoginToken> getLoginTokens() {
		return loginTokens;
	}

	private byte[] getRandomSalt() {
		final byte[] randmoSalt = new byte[16];
		RANDOM.nextBytes(randmoSalt);
		return randmoSalt;
	}

	public String getRole() {
		return role;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.hashPassword, this.salt);
	}

	public boolean isExpired() {
		return this.expired != null;
	}

	public boolean isValid(final String password) {
		Objects.requireNonNull(password);
		final byte[] typedPassword = this.buildHashPassword(password);
		return Arrays.equals(this.hashPassword, typedPassword);
	}

	public void setCreated(final LocalDate created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLoginTokens(Set<LoginToken> loginTokens) {
		this.loginTokens = loginTokens;
	}

	public void setPassword(final String password) {
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserName(final String username) {
		this.username = username;
	}
}
