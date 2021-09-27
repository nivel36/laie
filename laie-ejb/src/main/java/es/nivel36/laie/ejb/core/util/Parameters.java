package es.nivel36.laie.ejb.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Parameters implements Map<String, Object> {

	public static Parameters map(final String key, final Object value) {
		return new Parameters().and(key, value);
	}

	private final HashMap<String, Object> hashMap;

	private Parameters() {
		this.hashMap = new HashMap<>();
	}

	public Parameters and(final String key, final Object value) {
		this.hashMap.put(key, value);
		return this;
	}

	@Override
	public void clear() {
		this.hashMap.clear();
	}

	@Override
	public boolean containsKey(final Object key) {
		return this.hashMap.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return this.hashMap.containsValue(value);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return this.hashMap.entrySet();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Parameters other = (Parameters) obj;
		return Objects.equals(this.hashMap, other.hashMap);
	}

	@Override
	public Object get(final Object key) {
		return this.hashMap.get(key);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.hashMap);
	}

	@Override
	public boolean isEmpty() {
		return this.hashMap.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return this.hashMap.keySet();
	}

	@Override
	public Object put(final String key, final Object value) {
		return this.hashMap.put(key, value);
	}

	@Override
	public void putAll(final Map<? extends String, ? extends Object> m) {
		this.hashMap.putAll(m);
	}

	@Override
	public Object remove(final Object key) {
		return this.hashMap.remove(key);
	}

	@Override
	public int size() {
		return this.hashMap.size();
	}

	@Override
	public String toString() {
		return this.hashMap.toString();
	}

	@Override
	public Collection<Object> values() {
		return this.hashMap.values();
	}
}