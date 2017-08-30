package ged.ejb.core.events;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.Auditable;

@Interceptor
@Audited(action = Type.UNDELETE)
public class UndeleteInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final Event<Auditable> postUndeleteEvent;

	private final Event<Auditable> preUndeleteEvent;

	@Inject
	public UndeleteInterceptor(@PreUpdate final Event<Auditable> preUndeleteEvent,
			@PostUpdate final Event<Auditable> postUndeleteEvent) {
		Objects.requireNonNull(preUndeleteEvent);
		Objects.requireNonNull(postUndeleteEvent);
		this.preUndeleteEvent = preUndeleteEvent;
		this.postUndeleteEvent = postUndeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Auditable auditedEntity = getAuditedEntity(joinPoint);
		logger.trace("Undelete event fired");
		this.preUndeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postUndeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}