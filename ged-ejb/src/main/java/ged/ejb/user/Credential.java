package ged.ejb.user;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.security.CriptoUtil;

@Entity
public class Credential extends AbstractEntity {

	private static final Random RANDOM = new SecureRandom();

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

	public void setPassword(String password) {
		this.password = CriptoUtil.digestPassword(password, getSalt());
	}

	public void setUser(User user) {
		user.setCredential(this);
		this.user = user;
	}

	public boolean isValid(String password) {
		return Arrays.equals(this.password, CriptoUtil.digestPassword(password, salt));
	}

	private byte[] getSalt() {
		final byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
}
