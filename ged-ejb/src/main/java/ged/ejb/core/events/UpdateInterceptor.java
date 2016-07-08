package ged.ejb.core.events;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.AuditedEntity;

@Interceptor
@Audited(action = Type.Update)
public class UpdateInterceptor {

	@Inject
	@PostUpdate
	Event<AuditedEntity<Long>> postUpdateEvent;

	@Inject
	@PreUpdate
	Event<AuditedEntity<Long>> preUpdateEvent;

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			throw new IllegalArgumentException("Not audited entity");
		}
		@SuppressWarnings("unchecked")
		final AuditedEntity<Long> auditedEntity = (AuditedEntity<Long>) entityObject;
		this.preUpdateEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postUpdateEvent.fire(auditedEntity);
		return returnObject;
	}
}
