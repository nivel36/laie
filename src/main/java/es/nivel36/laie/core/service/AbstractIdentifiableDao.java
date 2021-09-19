package es.nivel36.laie.core.service;

public abstract class AbstractIdentifiableDao extends AbstractDao {

	protected abstract boolean findDuplicateUid(final String uid);

	////////////////////////////////////////////////////////////////////////////
	// PROTECTED
	////////////////////////////////////////////////////////////////////////////

	protected void generateUid(final Identifiable identifiable) {
		final RandomGenerator uidGenerator = new RandomGenerator(6);
		// The identifier is a randomly generated number. Although it generates a number
		// out of 16,777,216 and it is difficult for any number to be repeated, we must
		// always verify that the value is not duplicated.
		String uid;
		do {
			uid = uidGenerator.generateHex();
		} while (this.findDuplicateUid(uid));
		identifiable.setUid(uid);
	}
}
