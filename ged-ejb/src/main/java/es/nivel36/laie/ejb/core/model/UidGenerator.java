package es.nivel36.laie.ejb.core.model;

import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class UidGenerator {
	
	private UidGenerator() {}

	public static final String generate() {
		final Random random = ThreadLocalRandom.current();
		final byte[] bytes = new byte[6];
		random.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}
}
