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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false, unique = true)
	private User user;

	public Credential() {

	}

	public Credential(final User user, final String password) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(password);
		this.setPassword(password);
		this.setUser(user);
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
		return Arrays.equals(this.hashPassword, this.buildHashPassword(password));
	}

	public void setPassword(final String password) {
		this.created = LocalDate.now();
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
