package ged.ejb.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credential implements Serializable {
	
	private static final long serialVersionUID = 8026493839739887015L;
	
	@Column(length = 64, nullable = false)
	private char[] password;
	
	@Column(length = 16, nullable = false)
	private byte[] salt;

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
}
