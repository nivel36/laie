package ged.ejb.core.events;

import java.util.Objects;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.AuditedEntity;

@Interceptor
@Audited(action = Type.DELETE)
public class DeleteInterceptor extends AbstractInterceptor {

	private static final Logger log = LoggerFactory.getLogger(DeleteInterceptor.class);

	private final Event<AuditedEntity> postDeleteEvent;

	private final Event<AuditedEntity> preDeleteEvent;

	@Inject
	public DeleteInterceptor(@PreDelete final Event<AuditedEntity> preDeleteEvent,
			@PostDelete final Event<AuditedEntity> postDeleteEvent) {
		Objects.requireNonNull(preDeleteEvent);
		Objects.requireNonNull(postDeleteEvent);
		this.preDeleteEvent = preDeleteEvent;
		this.postDeleteEvent = postDeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final AuditedEntity auditedEntity = getAuditedEntity(joinPoint);
		log.trace("Delete event fired");
		this.preDeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postDeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}