package es.nivel36.login;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Credential implements Serializable {
	
	private static final long serialVersionUID = -7213946632987932359L;
	
	private static final Random RANDOM = new SecureRandom();

	private LocalDate created;

	private LocalDate expired;

	@Column(length = 32, nullable = false)
	private byte[] hashPassword;

	private Long id;

	@Column(length = 16, nullable = false)
	private byte[] salt;

	private String username;

	public Credential() {
	}

	public Credential(final String username, final String password) {
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
		final Credential other = (Credential) obj;
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

	private byte[] getRandomSalt() {
		final byte[] randmoSalt = new byte[16];
		RANDOM.nextBytes(randmoSalt);
		return randmoSalt;
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

	public void setPassword(final String password) {
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	public void setUserName(final String username) {
		this.username = username;
	}
}
