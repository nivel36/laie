package ged.web.core.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ METHOD, FIELD, TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface WebConfigurationProperty {

	@Nonbinding
	boolean required() default false;

	@Nonbinding
	String value() default "";
}
