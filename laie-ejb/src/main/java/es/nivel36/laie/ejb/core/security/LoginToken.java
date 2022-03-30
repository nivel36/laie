package es.nivel36.laie.ejb.core.security;

import static java.time.temporal.ChronoUnit.MONTHS;
import static javax.persistence.EnumType.STRING;

import java.time.Instant;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.nivel36.core.model.AbstractEntity;
import es.nivel36.laie.ejb.user.User;

@Entity
public class LoginToken extends AbstractEntity {

	public enum TokenType {
		API, REMEMBER_ME, RESET_PASSWORD, SIGNUP_GDPR
	}

	public static final int DESCRIPTION_MAXLENGTH = 255;

	private static final int HASH_LENGTH = 32;

	public static final int IP_ADDRESS_MAXLENGTH = 45;

	private static final long serialVersionUID = -8763367136110624639L;

	@Column(nullable = false)
	private @NotNull Instant created;

	@Column(length = DESCRIPTION_MAXLENGTH)
	private @Size(max = DESCRIPTION_MAXLENGTH) String description;

	@Column(nullable = false)
	private @NotNull Instant expiration;

	@Column(length = IP_ADDRESS_MAXLENGTH, nullable = false)
	private @NotNull @Size(max = IP_ADDRESS_MAXLENGTH) String ipAddress;

	@Column(length = HASH_LENGTH, nullable = false, unique = true)
	private @NotNull byte[] tokenHash;

	@Enumerated(STRING)
	private TokenType type;

	@ManyToOne(optional = false)
	private User user;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginToken other = (LoginToken) obj;
		return Arrays.equals(tokenHash, other.tokenHash);
	}

	public Instant getCreated() {
		return this.created;
	}

	public String getDescription() {
		return this.description;
	}

	public Instant getExpiration() {
		return this.expiration;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public byte[] getTokenHash() {
		return this.tokenHash;
	}

	public TokenType getType() {
		return this.type;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(tokenHash);
	}

	public void setCreated(final Instant created) {
		this.created = created;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setExpiration(final Instant expiration) {
		this.expiration = expiration;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@PrePersist
	public void setTimestamps() {
		this.created = Instant.now();
		if (this.expiration == null) {
			this.expiration = this.created.plus(1, MONTHS);
		}
	}

	public void setTokenHash(final byte[] tokenHash) {
		this.tokenHash = tokenHash;
	}

	public void setType(final TokenType type) {
		this.type = type;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
