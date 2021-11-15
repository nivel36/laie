package es.nivel36.laie.ejb.core.model;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class UidGenerator {

	private UidGenerator() {
	}

	public static final String generate(final Class<?> clazz) {
		final Random random = ThreadLocalRandom.current();
		final byte[] bytes = new byte[4];
		random.nextBytes(bytes);
		final String code = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).toUpperCase();
		final String prefix = Base64.getUrlEncoder().withoutPadding().encodeToString(getPrefix(clazz)).toUpperCase();
		return prefix + code;
	}

	private static byte[] getPrefix(Class<?> clazz) {
		final int hash = clazz.hashCode();
		return ByteBuffer.allocate(4).putInt(hash).array();
	}
}
