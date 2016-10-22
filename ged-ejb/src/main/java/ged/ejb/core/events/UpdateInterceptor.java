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
public class UpdateInterceptor extends AbstractInterceptor {

	private static final Logger log = Logger.getLogger(UpdateInterceptor.class.getName());

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
		final AuditedEntity<Long> auditedEntity = getAuditedEntity(joinPoint);
		log.finer("Update event fired");
		this.preUpdateEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postUpdateEvent.fire(auditedEntity);
		return returnObject;
	}
}