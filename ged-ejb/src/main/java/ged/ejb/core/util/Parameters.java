package ged.ejb.core.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Parameters implements Map<String, Object>, Serializable {

	private static final long serialVersionUID = 5597385039960202946L;

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

	
	public void clear() {
		this.hashMap.clear();
	}

	
	public boolean containsKey(final Object key) {
		return this.hashMap.containsKey(key);
	}

	
	public boolean containsValue(final Object value) {
		return this.hashMap.containsValue(value);
	}

	
	public Set<Entry<String, Object>> entrySet() {
		return this.hashMap.entrySet();
	}

	
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

	
	public Object get(final Object key) {
		return this.hashMap.get(key);
	}

	
	public int hashCode() {
		return Objects.hash(this.hashMap);
	}

	
	public boolean isEmpty() {
		return this.hashMap.isEmpty();
	}

	
	public Set<String> keySet() {
		return this.hashMap.keySet();
	}

	
	public Object put(final String key, final Object value) {
		return this.hashMap.put(key, value);
	}

	
	public void putAll(final Map<? extends String, ? extends Object> m) {
		this.hashMap.putAll(m);
	}

	
	public Object remove(final Object key) {
		return this.hashMap.remove(key);
	}

	
	public int size() {
		return this.hashMap.size();
	}

	
	public String toString() {
		return this.hashMap.toString();
	}

	
	public Collection<Object> values() {
		return this.hashMap.values();
	}
}