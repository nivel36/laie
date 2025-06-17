package es.nivel36.login;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ACCOUNT", //
		indexes = { @Index(name = "UX_ACCOUNT_USENAME", columnList = "USERNAME", unique = true) }, //
		uniqueConstraints = { @UniqueConstraint(name = "UQ_PERSON_USERNAME", columnNames = { "USERNAME" }) })
public class Account implements Serializable {

	private static final Random RANDOM = new SecureRandom();

	private static final long serialVersionUID = -7213946632987932359L;

	@Column(name = "CREATED", nullable = false)
	private LocalDate created;

	@Column(name = "EXPIRED")
	private LocalDate expired;

	@Column(name = "HASH_PASSWORD", nullable = false)
	private String hashPassword;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@OneToMany(mappedBy = "account", orphanRemoval = true)
	private Set<LoginToken> loginTokens;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "SALT", nullable = false)
	private byte[] salt;

	@Column(name = "USERNAME", nullable = false)
	private String username;

	public Account() {
	}

	public Account(final String username, final String password) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(username);
		this.salt = new byte[16];
		this.created = LocalDate.now();
		this.username = username;
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	private String buildHashPassword(final String password) {
		return CriptoUtil.digestPassword(password, this.salt);
	}

        @Override
        public boolean equals(final Object obj) {
                if (this == obj) {
                        return true;
                }
                if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
                        return false;
                }
                final Account other = (Account) obj;
                return Objects.equals(this.hashPassword, other.hashPassword) && java.util.Arrays.equals(this.salt, other.salt);
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
                return 31 * Objects.hashCode(this.hashPassword) + java.util.Arrays.hashCode(this.salt);
        }

	public boolean isExpired() {
		return this.expired != null;
	}

	public boolean isValid(final String password) {
		Objects.requireNonNull(password);
		final String typedPassword = this.buildHashPassword(password);
		return this.hashPassword.equals(typedPassword);
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

        public void setUsername(final String username) {
                this.username = username;
        }
}
