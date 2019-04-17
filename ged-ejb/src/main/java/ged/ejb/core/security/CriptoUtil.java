package ged.ejb.core.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.xml.bind.DatatypeConverter;

public class CriptoUtil {

	private static final String SHA_256 = "SHA-256";

	private CriptoUtil() {
	}

	public static byte[] digestPassword(final String password, byte[] salt) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(salt);
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
			messageDigest.update(salt);
			return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	public static byte[] toBase64(final byte[] characters) {
		Objects.requireNonNull(characters);
		return DatatypeConverter.printBase64Binary(characters).getBytes();
	}
}
