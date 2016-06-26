package ged.ejb.core.action;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ged.ejb.core.model.AuditedEntity;

@Interceptor
@Audited
public class ActionInterceptor {

	@Inject
	private ActionService actionService;

	@AroundInvoke
	public Object addAction(final InvocationContext joinPoint) throws Exception {
		try {
			return joinPoint.proceed();
		} finally {
			final Object[] parameters = joinPoint.getParameters();
			if (parameters.length != 1) {
				throw new IllegalArgumentException("Arguments: " + parameters.length);
			}
			final Object entityObject = parameters[0];
			if (!(entityObject instanceof AuditedEntity)) {
				throw new IllegalArgumentException("Not audited entity");
			}
			final AuditedEntity auditedEntity = (AuditedEntity) entityObject;
			this.actionService.addAction(auditedEntity);
		}
	}
}
