package ged.ejb.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Properties {

	private final Map<String, String> propertiesMap = new HashMap<>();

	public void readFile() throws IOException {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final InputStream input = classLoader.getResourceAsStream("foo.properties");
		final java.util.Properties properties = new java.util.Properties();
		properties.load(input);
	}

}
