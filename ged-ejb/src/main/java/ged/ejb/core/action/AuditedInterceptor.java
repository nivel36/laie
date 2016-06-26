package ged.ejb.core.action;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ged.ejb.core.model.AuditedEntity;

@Interceptor
@Audited
public class AuditedInterceptor {

	@Inject
	private ActionService actionService;

	@AroundInvoke
	public Object addAction(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			throw new IllegalArgumentException("Not audited entity");
		}
		final AuditedEntity auditedEntity = (AuditedEntity) entityObject;
		try {
			return joinPoint.proceed();
		} finally {
			final Audited annotation = joinPoint.getMethod().getAnnotation(Audited.class);
			final String action = annotation.action();
			this.actionService.addAction(auditedEntity, action);
		}
	}
}