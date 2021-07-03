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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resources {

	private static final String LAIE_CONFIG = "LAIE_CONFIG";

	private static final Logger logger = LoggerFactory.getLogger(Resources.class);

	private final Properties properties = new Properties();

	private String getConfigFileLocation() {
		final String configFileLocation = System.getenv(LAIE_CONFIG);
		if (configFileLocation != null) {
			return configFileLocation;
		}
		return System.getProperty(LAIE_CONFIG);
	}

	@PostConstruct
	public void init() {
		this.readProperties();
	}

	@Produces
	@ConfigurationProperty
	public String produceProperty(final InjectionPoint ip) {
		final ConfigurationProperty annotation = ip.getAnnotated().getAnnotation(ConfigurationProperty.class);
		final String key = annotation.value();
		final String value = this.properties.getProperty(key);
		if (value == null) {
			final boolean valueRequired = annotation.required();
			if (valueRequired) {
				throw new IllegalStateException("Property not found: " + key);
			}
		}
		return value;
	}

	private void readConfigFileFromFileSystem(final String configFileLocation) {
		logger.info("Reading config file from {}", configFileLocation);
		final Path path = Paths.get(configFileLocation);
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			this.properties.load(reader);
		} catch (final IOException e) {
			logger.error("Property file not found", e);
		}
	}

	private void readDefaultConfigFile() {
		logger.warn("Config file not set. Using default config file");
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try (InputStream inputStream = cl.getResourceAsStream("/es/nivel36/laie/default.config")) {
			this.properties.load(inputStream);
		} catch (final IOException e) {
			logger.error("Property file not found", e);
		}
	}

	private void readProperties() {
		final String configFileLocation = this.getConfigFileLocation();
		if (configFileLocation != null) {
			this.readConfigFileFromFileSystem(configFileLocation);
		} else {
			this.readDefaultConfigFile();
		}
	}
}