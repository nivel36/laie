/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.login.token;

import static javax.persistence.EnumType.STRING;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import es.nivel36.laie.core.service.AbstractEntity;
import es.nivel36.laie.user.User;

/**
 * A token to manage the user's login.
 * 
 * <p>
 * <strong>Value:</strong><br/>
 * At the time of login the user is provided with a random text string that is
 * validated each time the user performs an action. This string is a random
 * value to which a SHA-256 algorithm is applied.
 * 
 * <p>
 * <strong>Token expiration:</strong><br/>
 * When the token is generated it is assigned two dates, the creation date and
 * the expiration date. The expiration date is checked each time the user
 * performs an action. If the current date is greater than the expiration date,
 * the token is invalidated and the user must login again.
 * 
 * <p>
 * <strong>Token type:</strong><br/>
 * There are three types of tokens:
 * <ul>
 * <li><i>API</i>, it is the default token, a user logs in and can access the
 * application services, its duration time is that of a normal maxAge, 30
 * minutes.</li>
 * <li><i>REMEMBER_ME</i>, if the user wants to keep the maxAge open this token
 * is assigned. The token will be active for a much longer time.</li>
 * <li><i>RESET_PASSWORD</i>. Temporary token that is assigned so that the user
 * can access the password reset screens.</li>
 * </ul>
 * 
 * @author Abel Ferrer
 *
 */
@Entity
public class LoginToken extends AbstractEntity {

	private static final long serialVersionUID = 6577406144285463245L;

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	public enum TokenType {
		API, REMEMBER_ME, RESET_PASSWORD
	}

	@Column(nullable = false)
	private @NotNull Instant created;

	private String description;

	@Column(nullable = false)
	private @NotNull Instant expiration;

	@Column(nullable = false)
	private @NotNull String ipAddress;

	@Column(nullable = false, unique = true)
	private @NotNull byte[] tokenHash;

	@Enumerated(STRING)
	private TokenType type;

	@ManyToOne(optional = false)
	private User user;

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		final LoginToken other = (LoginToken) obj;
		return Arrays.equals(this.tokenHash, other.tokenHash);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(this.tokenHash);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new String(this.tokenHash, StandardCharsets.UTF_8);
	}

	////////////////////////////////////////////////////////////////////////////
	// GETTERS
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the token creation date. This value can't be null.
	 * 
	 * @return <tt>Instant<tt> with the token creation date.
	 */
	public Instant getCreated() {
		return this.created;
	}

	/**
	 * Returns the token description. This description is for informational purposes
	 * only and is not for authentication purposes.
	 * 
	 * @return <tt>String</tt> with the token description or <tt>null</tt> if the
	 *         token has no description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Returns the token expiration date. This value can't be null.
	 * 
	 * @return <tt>Instant<tt> with the token expiration date.
	 */
	public Instant getExpiration() {
		return this.expiration;
	}

	/**
	 * Returns the ip address from which the application was accessed. This value
	 * can not be null.
	 * 
	 * @return <tt>String</tt> with the ip address from which the application was
	 *         accessed.
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * Returns the token hash. This value is a random value to which a SHA-256
	 * algorithm is applied. This value is unique and not null.
	 * 
	 * @return <tt>byte[]</tt> with the token hash.
	 */
	public byte[] getTokenHash() {
		return this.tokenHash;
	}

	/**
	 * Returns the token type. There are three types of tokens:
	 * <ul>
	 * <li><i>API</i>, it is the default token, a user logs in and can access the
	 * application services, its duration time is that of a normal maxAge, 30
	 * minutes.</li>
	 * <li><i>REMEMBER_ME</i>, if the user wants to keep the maxAge open this token
	 * is assigned. The token will be active for a much longer time.</li>
	 * <li><i>RESET_PASSWORD</i>. Temporary token that is assigned so that the user
	 * can access the password reset screens.</li>
	 * </ul>
	 * 
	 * @return <tt>TokenType</tt> with the token type.
	 */
	public TokenType getType() {
		return this.type;
	}

	/**
	 * Returns the user associated to the token.
	 * 
	 * A user can have multiple tokens associated with him/her. For example, he/she
	 * can have one token associated to his/her cell phone and another one to
	 * his/her computer.
	 * 
	 * @return <tt>User</tt> associated to the token
	 */
	public User getUser() {
		return this.user;
	}

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////

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
