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
@Audited(action = Type.UNDELETE)
public class UndeleteInterceptor {

	private final static Logger log = Logger.getLogger(UndeleteInterceptor.class.getName());

	private final Event<AuditedEntity<Long>> postUndeleteEvent;

	private final Event<AuditedEntity<Long>> preUndeleteEvent;

	@Inject
	public UndeleteInterceptor(@PreUpdate final Event<AuditedEntity<Long>> preUndeleteEvent,
			@PostUpdate final Event<AuditedEntity<Long>> postUndeleteEvent) {
		if (preUndeleteEvent == null) {
			throw new NullPointerException();
		}
		if (postUndeleteEvent == null) {
			throw new NullPointerException();
		}
		this.preUndeleteEvent = preUndeleteEvent;
		this.postUndeleteEvent = postUndeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		log.finer("Undelete event fired");
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			log.severe("Not audited entity");
			throw new IllegalArgumentException("Not audited entity");
		}
		@SuppressWarnings("unchecked")
		final AuditedEntity<Long> auditedEntity = (AuditedEntity<Long>) entityObject;
		this.preUndeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postUndeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}