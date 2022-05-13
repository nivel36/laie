package es.nivel36.laie.ejb.core.action;

import javax.interceptor.InvocationContext;

public abstract class AbstractInterceptor {

	protected Auditable getAuditedEntity(final InvocationContext joinPoint) {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof Auditable)) {
			throw new IllegalArgumentException("Not audited entity");
		}
		return (Auditable) entityObject;
	}
}