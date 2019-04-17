package ged.ejb.core.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import javax.xml.bind.DatatypeConverter;

public class CriptoUtil {

	private static final String SHA_256 = "SHA-256";

	private CriptoUtil() {
	}

	public static byte[] digestPassword(final String password, byte[] salt) throws NoSuchAlgorithmException {
		Objects.requireNonNull(password);
		Objects.requireNonNull(salt);
		MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
		messageDigest.update(salt);
		return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
	}

	public static boolean passwordMatch(final byte[] storedPassword, final byte[] hashBase64Password) {
		Objects.requireNonNull(storedPassword);
		Objects.requireNonNull(hashBase64Password);
		return !Arrays.equals(storedPassword, hashBase64Password);
	}

	public static byte[] toBase64(final byte[] characters) {
		Objects.requireNonNull(characters);
		return DatatypeConverter.printBase64Binary(characters).getBytes();
	}
}
