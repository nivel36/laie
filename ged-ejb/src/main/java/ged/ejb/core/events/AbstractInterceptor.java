package ged.ejb.core.events;

import javax.interceptor.InvocationContext;

import ged.ejb.core.model.AuditedEntity;

public abstract class AbstractInterceptor {

	@SuppressWarnings("unchecked")
	protected AuditedEntity<Long> getAuditedEntity(final InvocationContext joinPoint) {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			throw new IllegalArgumentException("Not audited entity");
		}
		return (AuditedEntity<Long>) entityObject;
	}

}