package es.nivel36.core.model;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public final class UidGenerator {
	
	private static final SecureRandom random = new SecureRandom();

	private UidGenerator() {
	}

	public static final String generate(final Class<?> clazz) {
		final byte[] bytes = new byte[4];
		random.nextBytes(bytes);
		return encodeToString(bytes);
	}
	
	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
	
	public static String encodeToString(byte[] bytes) {
	    byte[] hexChars = new byte[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars, StandardCharsets.UTF_8);
	}
}
