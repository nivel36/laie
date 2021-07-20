/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaieConfigSource implements ConfigSource {

	private static final String LAIE_CONFIG = "LAIE_CONFIG";

	private static final Logger logger = LoggerFactory.getLogger(LaieConfigSource.class);

	private final String filePath;

	private final Map<String, String> properties = new HashMap<>();

	public LaieConfigSource() {
		this.filePath = this.getConfigFileLocation();
		if (this.filePath != null) {
			this.init();
		}
	}

	private void fillPropertiesMap(final Properties properties) {
		properties.clear();
		for (final Entry<Object, Object> entry : properties.entrySet()) {
			final String key = (String) entry.getKey();
			final String value = (String) entry.getValue();
			properties.put(key, value);
		}
	}

	private String getConfigFileLocation() {
		final String configFileLocation = System.getenv(LAIE_CONFIG);
		if (configFileLocation != null) {
			return configFileLocation;
		}
		return System.getProperty(LAIE_CONFIG);
	}

	@Override
	public String getName() {
		return "Laie config file: " + this.filePath;
	}

	@Override
	public int getOrdinal() {
		return 900;
	}

	@Override
	public Map<String, String> getProperties() {
		if (this.filePath != null) {
			this.init();
		}
		return this.properties;
	}

	@Override
	public Set<String> getPropertyNames() {
		return this.properties.keySet();
	}

	@Override
	public String getValue(final String propertyName) {
		return this.properties.get(propertyName);
	}

	private void init() {
		logger.info("Reading config file from {}", this.filePath);
		final Path path = Paths.get(this.filePath);
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			final Properties properties = new Properties();
			properties.load(reader);
			this.fillPropertiesMap(properties);
		} catch (final IOException e) {
			logger.error("Property file not found", e);
		}
	}
}
