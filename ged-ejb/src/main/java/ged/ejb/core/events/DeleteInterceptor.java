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
@Audited(action = Type.DELETE)
public class DeleteInterceptor {

	private final static Logger log = Logger.getLogger(DeleteInterceptor.class.getName());

	private final Event<AuditedEntity<Long>> postDeleteEvent;

	private final Event<AuditedEntity<Long>> preDeleteEvent;

	@Inject
	public DeleteInterceptor(@PreDelete final Event<AuditedEntity<Long>> preDeleteEvent,
			@PostDelete final Event<AuditedEntity<Long>> postDeleteEvent) {
		if (preDeleteEvent == null) {
			throw new NullPointerException();
		}
		if (postDeleteEvent == null) {
			throw new NullPointerException();
		}
		this.preDeleteEvent = preDeleteEvent;
		this.postDeleteEvent = postDeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		log.finer("Delete event fired");
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			log.severe("Not audited entity");
			throw new IllegalArgumentException("Not audited entity");
		}
		@SuppressWarnings("unchecked")
		final AuditedEntity<Long> auditedEntity = (AuditedEntity<Long>) entityObject;
		this.preDeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postDeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}