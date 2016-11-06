package ged.ejb.core.events;

import java.util.Objects;
import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.AuditedEntity;

@Interceptor
@Audited(action = Type.DELETE)
public class DeleteInterceptor extends AbstractInterceptor {

	private static final Logger log = Logger.getLogger(DeleteInterceptor.class.getName());

	private final Event<AuditedEntity<Long>> postDeleteEvent;

	private final Event<AuditedEntity<Long>> preDeleteEvent;

	@Inject
	public DeleteInterceptor(@PreDelete final Event<AuditedEntity<Long>> preDeleteEvent,
			@PostDelete final Event<AuditedEntity<Long>> postDeleteEvent) {
		Objects.requireNonNull(preDeleteEvent);
		Objects.requireNonNull(postDeleteEvent);
		this.preDeleteEvent = preDeleteEvent;
		this.postDeleteEvent = postDeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final AuditedEntity<Long> auditedEntity = getAuditedEntity(joinPoint);
		log.finer("Delete event fired");
		this.preDeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postDeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}