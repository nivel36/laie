package ged.ejb.core.events;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Audited {

	public enum Type {
		DELETE, PERSIST, UNDELETE
	};

	Type action();
}
