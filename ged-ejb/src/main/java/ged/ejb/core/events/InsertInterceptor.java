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
@Audited(action = Type.PERSIST)
public class InsertInterceptor extends AbstractInterceptor {

	private static final Logger log = LoggerFactory.getLogger(InsertInterceptor.class.getName());

	private final Event<AuditedEntity> postPersistEvent;

	private final Event<AuditedEntity> prePersistEvent;

	@Inject
	public InsertInterceptor(@PostPersist final Event<AuditedEntity> postPersistEvent,
			@PrePersist final Event<AuditedEntity> prePersistEvent) {
		Objects.requireNonNull(postPersistEvent);
		Objects.requireNonNull(prePersistEvent);
		this.postPersistEvent = postPersistEvent;
		this.prePersistEvent = prePersistEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final AuditedEntity auditedEntity = getAuditedEntity(joinPoint);
		log.trace("Insert event fired");
		this.prePersistEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postPersistEvent.fire(auditedEntity);
		return returnObject;
	}
}