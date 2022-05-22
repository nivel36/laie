package es.nivel36.login;

import static javax.persistence.EnumType.STRING;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LoginToken  implements Serializable {

	public enum TokenType {
		GUEST, REMEMBER_ME, RESET_PASSWORD
	}

	private static final long serialVersionUID = 6700658371126462629L;

	@Column(nullable = false)
	private Instant created;

	private String description;

	@Column(nullable = false)
	private Instant expiration;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String ipAddress;

	@Column(nullable = false, unique = true)
	private byte[] tokenHash;

	@Enumerated(STRING)
	private TokenType type;

	@ManyToOne
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

	public long getId() {
		return id;
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

	public Account getAccount() {
		return this.account;
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

	public void setId(long id) {
		this.id = id;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setTokenHash(final byte[] tokenHash) {
		this.tokenHash = tokenHash;
	}
	
	public void setType(final TokenType type) {
		this.type = type;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}
}
