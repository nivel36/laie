package es.nivel36.laie.ejb.core.model;

import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

public final class UidGenerator {

	private UidGenerator() {
	}

	public static final String generate(final Class<?> clazz) {
		final Random random = ThreadLocalRandom.current();
		final byte[] bytes = new byte[6];
		random.nextBytes(bytes);
		final String code = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).toUpperCase();
		final String prefix = getPrefix(clazz);
		return prefix + code;
	}

	private static String getPrefix(Class<?> clazz) {
		if (clazz.equals(User.class)) {
			return "0";
		}
		if (clazz.equals(Candidate.class)) {
			return "1";
		}
		if (clazz.equals(Meeting.class)) {
			return "2";
		}
		if (clazz.equals(JobOffer.class)) {
			return "3";
		}
		if (clazz.equals(Curriculum.class)) {
			return "4";
		}
		throw new IllegalStateException(clazz.getCanonicalName());
	}
}
