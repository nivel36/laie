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
@Audited(action = Type.INSERT)
public class InsertInterceptor {

	private final static Logger log = Logger.getLogger(InsertInterceptor.class.getName());

	private final Event<AuditedEntity<Long>> postPersistEvent;

	private final Event<AuditedEntity<Long>> prePersistEvent;

	@Inject
	public InsertInterceptor(@PostPersist final Event<AuditedEntity<Long>> postPersistEvent,
			@PrePersist final Event<AuditedEntity<Long>> prePersistEvent) {
		if (postPersistEvent == null) {
			throw new NullPointerException();
		}
		if (prePersistEvent == null) {
			throw new NullPointerException();
		}
		this.postPersistEvent = postPersistEvent;
		this.prePersistEvent = prePersistEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		log.finer("Insert event fired");
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof AuditedEntity)) {
			log.severe("Not audited entity");
			throw new IllegalArgumentException("Not audited entity");
		}
		@SuppressWarnings("unchecked")
		final AuditedEntity<Long> auditedEntity = (AuditedEntity<Long>) entityObject;
		this.prePersistEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postPersistEvent.fire(auditedEntity);
		return returnObject;
	}
}