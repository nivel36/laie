package ged.ejb.core.events;

import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.AuditedEntity;

@Interceptor
@Audited(action = Type.UPDATE)
public class UpdateInterceptor {

	private final static Logger log = Logger.getLogger(UpdateInterceptor.class.getName());

	private final Event<AuditedEntity<Long>> postUpdateEvent;

	private final Event<AuditedEntity<Long>> preUpdateEvent;

	@Inject
	public UpdateInterceptor(@PreUpdate final Event<AuditedEntity<Long>> preUpdateEvent,
			@PostUpdate final Event<AuditedEntity<Long>> postUpdateEvent) {
		if (preUpdateEvent == null) {
			throw new NullPointerException();
		}
		if (postUpdateEvent == null) {
			throw new NullPointerException();
		}
		this.preUpdateEvent = preUpdateEvent;
		this.postUpdateEvent = postUpdateEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		log.finer("Update event fired");
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			log.severe("Not audited entity");
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