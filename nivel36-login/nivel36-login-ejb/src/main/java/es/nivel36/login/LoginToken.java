package es.nivel36.login;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "LOGIN_TOKEN", indexes = {
		@Index(name = "UX_LOGIN_TOKEN_TOKEN_HASH", unique = true, columnList = "TOKEN_HASH") }, uniqueConstraints = {
				@UniqueConstraint(name = "UQ_LOGIN_TOKEN_TOKEN_HASH", columnNames = { "TOKEN_HASH" }) })
public class LoginToken implements Serializable {

	public enum TokenType {
		GUEST, REMEMBER_ME, RESET_PASSWORD
	}

	private static final long serialVersionUID = 6700658371126462629L;

	@Column(name = "CREATED", nullable = false)
	private Instant created;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EXPIRATION")
	private Instant expiration;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "TOKEN_HASH", nullable = false, unique = true)
	private String tokenHash;

	@Enumerated(EnumType.STRING)
	@Column(name = "TOKEN_TYPE")
	private TokenType type;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginToken other = (LoginToken) obj;
		return tokenHash.equals(other.tokenHash);
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

	public long getId() {
		return id;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public String getTokenHash() {
		return this.tokenHash;
	}

	public TokenType getType() {
		return this.type;
	}

	public Account getAccount() {
		return this.account;
	}

	@Override
	public int hashCode() {
		return tokenHash.hashCode();
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

	public void setId(long id) {
		this.id = id;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setTokenHash(final String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public void setType(final TokenType type) {
		this.type = type;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}
}
