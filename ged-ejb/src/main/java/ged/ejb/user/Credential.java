package ged.ejb.user;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.security.CriptoUtil;

@Entity
public class Credential extends AbstractEntity {

	private static final Random RANDOM = new SecureRandom();

	private static final long serialVersionUID = 8026493839739887015L;

	@NotNull
	private LocalDate created;

	private LocalDate expired;

	@Column(length = 32, nullable = false)
	@NotNull
	private byte[] hashPassword = new byte[32];

	@Column(length = 16, nullable = false)
	@NotNull
	private byte[] salt = new byte[16];

	public Credential() {

	}

	public Credential(final String password) {
		Objects.requireNonNull(password);
		this.newCredential(password);
	}

	private byte[] buildHashPassword(final String password) {
		return CriptoUtil.digestPassword(password, this.salt);
	}

	public void expire() {
		this.expired = LocalDate.now();
	}

	private byte[] getRandomSalt() {
		final byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public boolean isExpired() {
		return this.expired != null;
	}

	public boolean isValid(final String password) {
		Objects.requireNonNull(password);
		return Arrays.equals(this.hashPassword, this.buildHashPassword(password));
	}

	private void newCredential(final String password) {
		this.created = LocalDate.now();
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	public void setPassword(final String password) {
		Objects.requireNonNull(password);
		this.newCredential(password);
	}
}
