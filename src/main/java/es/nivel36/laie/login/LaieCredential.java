package es.nivel36.laie.login;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractEntity;
import es.nivel36.laie.core.service.RandomGenerator;
import es.nivel36.laie.user.User;

@Entity
@Table(name = "CREDENTIAL")
public class LaieCredential extends AbstractEntity {

	private static final long serialVersionUID = -8437931543946150533L;

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

	public LaieCredential() {
	}

	public LaieCredential(final User user, final String password) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(user);
		this.hashPassword = new byte[32];
		this.salt = new byte[16];
		this.created = LocalDate.now();
		this.user = user;
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	private byte[] buildHashPassword(final String password) {
		MessageDigester md = new MessageDigester(password, this.salt);
		return md.digest();
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
		final LaieCredential other = (LaieCredential) obj;
		return Objects.equals(this.hashPassword, other.hashPassword) && Objects.equals(this.salt, other.salt);
	}

	public void expire() {
		this.expired = LocalDate.now();
	}

	public LocalDate getCreated() {
		return this.created;
	}

	private byte[] getRandomSalt() {
		return new RandomGenerator(16).generate();
	}

	public User getUser() {
		return this.user;
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

	public void setPassword(final String password) {
		this.salt = this.getRandomSalt();
		this.hashPassword = this.buildHashPassword(password);
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
