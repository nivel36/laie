package ged.ejb.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ged.ejb.core.model.AbstractEntity;

@Entity
public class Credential extends AbstractEntity {

	private static final long serialVersionUID = 8026493839739887015L;

	@Column(length = 32, nullable = false)
	@NotNull
	private byte[] password;

	@Column(length = 16, nullable = false)
	@NotNull
	private byte[] salt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = true)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@NotNull
	private User user;

	public byte[] getPassword() {
		return password;
	}

	public byte[] getSalt() {
		return salt;
	}

	public User getUser() {
		return user;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
