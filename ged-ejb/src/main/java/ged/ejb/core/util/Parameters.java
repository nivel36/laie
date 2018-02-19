package ged.ejb.core.util;

import java.util.HashMap;

public class Parameters extends HashMap<String, Object> {

	private static final long serialVersionUID = 5748269088319945681L;

	public static Parameters map(final String key, final Object value) {
		return new Parameters().and(key, value);
	}

	public Parameters and(final String key, final Object value) {
		put(key, value);
		return this;
	}
}