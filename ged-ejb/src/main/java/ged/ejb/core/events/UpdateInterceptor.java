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

import ged.ejb.core.Audited;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Auditable;

@Interceptor
@Audited(action = ActionType.UPDATE)
public class UpdateInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final Event<Auditable> postUpdateEvent;

	private final Event<Auditable> preUpdateEvent;

	@Inject
	public UpdateInterceptor(@PostUpdate final Event<Auditable> postPersistEvent,
			@PrePersist final Event<Auditable> prePersistEvent) {
		Objects.requireNonNull(postPersistEvent);
		Objects.requireNonNull(prePersistEvent);
		this.postUpdateEvent = postPersistEvent;
		this.preUpdateEvent = prePersistEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Auditable auditedEntity = getAuditedEntity(joinPoint);
		logger.trace("Insert event fired");
		this.preUpdateEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postUpdateEvent.fire(auditedEntity);
		return returnObject;
	}
}