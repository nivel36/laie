package ged.ejb.core.model;

import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UidGenerator {
	
	private UidGenerator() {}

	public static String generate() {
		final Random random = ThreadLocalRandom.current();
		final byte[] bytes = new byte[6];
		random.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}
}
