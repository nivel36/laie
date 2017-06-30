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
@Audited(action = Type.UNDELETE)
public class UndeleteInterceptor extends AbstractInterceptor {

	private static final Logger log = LoggerFactory.getLogger(UndeleteInterceptor.class.getName());

	private final Event<AuditedEntity> postUndeleteEvent;

	private final Event<AuditedEntity> preUndeleteEvent;

	@Inject
	public UndeleteInterceptor(@PreUpdate final Event<AuditedEntity> preUndeleteEvent,
			@PostUpdate final Event<AuditedEntity> postUndeleteEvent) {
		Objects.requireNonNull(preUndeleteEvent);
		Objects.requireNonNull(postUndeleteEvent);
		this.preUndeleteEvent = preUndeleteEvent;
		this.postUndeleteEvent = postUndeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final AuditedEntity auditedEntity = getAuditedEntity(joinPoint);
		log.trace("Undelete event fired");
		this.preUndeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postUndeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}