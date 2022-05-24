package es.nivel36.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class CriptoUtil {

	private static final String SHA_256 = "SHA-256";

	public static byte[] digestPassword(final String password) {
		Objects.requireNonNull(password);
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
			return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	public static byte[] digestPassword(final String password, final byte[] salt) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(salt);
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
			messageDigest.update(salt);
			return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	private CriptoUtil() {
	}
}
