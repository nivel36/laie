package es.nivel36.laie.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MessageDigester {

	private final static String SHA_256 = "SHA-256";

	private final MessageDigest messageDigest;

	private final byte[] password;

	private final byte[] salt;

	public MessageDigester(final String password) {
		this(password, null);
	}

	public MessageDigester(final String password, final byte[] salt) {
		try {
			messageDigest = MessageDigest.getInstance(SHA_256);
			this.password = password.getBytes(StandardCharsets.UTF_8);
			this.salt = salt;
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	public byte[] digest() {
		if (salt != null) {
			messageDigest.update(salt);
		}
		return messageDigest.digest(password);
	}
}
