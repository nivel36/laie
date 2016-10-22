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
public class InsertInterceptor extends AbstractInterceptor {

	private static final Logger log = Logger.getLogger(InsertInterceptor.class.getName());

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
		final AuditedEntity<Long> auditedEntity = getAuditedEntity(joinPoint);
		log.finer("Insert event fired");
		this.prePersistEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postPersistEvent.fire(auditedEntity);
		return returnObject;
	}
}