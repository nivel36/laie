package es.nivel36.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class CriptoUtil {

	private static final String SHA_256 = "SHA-256";

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String digestPassword(final String password) {
		Objects.requireNonNull(password);
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
			final byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	public static String digestPassword(final String password, final byte[] salt) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(salt);
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
			messageDigest.update(salt);
			final byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			char[] hexChars = new char[bytes.length * 2];
			for (int j = 0; j < bytes.length; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 2] = HEX_ARRAY[v >>> 4];
				hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
			}
			return new String(hexChars);
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	private CriptoUtil() {
	}
}
