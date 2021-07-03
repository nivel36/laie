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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * This annotation qualifies a property as a variable to be read from a
 * configuration file.
 * <p>
 * It accepts two values, the default value which is the name of the property in
 * the configuration file and the "required" property which defines whether a
 * value other than null is required to exist in the property. This qualifier
 * has to be used together with @Inject.
 * </p>
 * 
 * @author Abel Ferrer
 *
 */
@Qualifier
@Target({ METHOD, FIELD, TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationProperty {

	/**
	 * If a mandatory value is required in the property it must have a value of
	 * <tt>true</tt>, <tt>false</tt> otherwise
	 */
	@Nonbinding
	boolean required() default false;

	/**
	 * The name of the property in the property file.
	 */
	@Nonbinding
	String value() default "";
}
