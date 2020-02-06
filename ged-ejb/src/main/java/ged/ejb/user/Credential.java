package ged.ejb.user;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.security.CriptoUtil;

@Entity
public class Credential extends AbstractEntity {

	private static final Random RANDOM = new SecureRandom();

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private LocalDate created;

	private LocalDate expired;

	@Column(length = 32, nullable = false)
	@NotNull
	private byte[] hashPassword;

	@Column(length = 16, nullable = false)
	@NotNull
	private byte[] salt;

	@OneToOne(fetch = FetchType.EAGER, optional = false, orphanRemoval = true)
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	@NotNull
	private User user;

	public Credential() {
		this.hashPassword = new byte[32];
		this.salt = new byte[16];
		this.created = LocalDate.now();
	}

	public Credential(User user, final String password) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(user);
		this.hashPassword = new byte[32];
		this.salt = new byte[16];
		this.created = LocalDate.now();
		this.newCredential(user, password);
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

	private byte[] getRandomSalt() {
		final byte[] randmoSalt = new byte[16];
		RANDOM.nextBytes(randmoSalt);
		return randmoSalt;
	}

	public User getUser() {
		return user;
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

	private void newCredential(final User user, final String password) {
		this.user = user;
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}
	
	public void setPassword(final String password) {
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	public void setCreated(final LocalDate created) {
		this.created = created;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
