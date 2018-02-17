package ged.ejb.core.model;

import java.util.HashMap;

public class FluentHashMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 5748269088319945681L;

	public static FluentHashMap map(final String key, final Object value) {
		return new FluentHashMap().and(key, value);
	}

	public FluentHashMap and(final String key, final Object value) {
		put(key, value);
		return this;
	}
}