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
@Audited(action = ActionType.DELETE)
public class DeleteInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final Event<Auditable> postDeleteEvent;

	private final Event<Auditable> preDeleteEvent;

	@Inject
	public DeleteInterceptor(@PreDelete final Event<Auditable> preDeleteEvent,
			@PostDelete final Event<Auditable> postDeleteEvent) {
		Objects.requireNonNull(preDeleteEvent);
		Objects.requireNonNull(postDeleteEvent);
		this.preDeleteEvent = preDeleteEvent;
		this.postDeleteEvent = postDeleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Auditable auditedEntity = getAuditedEntity(joinPoint);
		logger.trace("Delete event fired");
		this.preDeleteEvent.fire(auditedEntity);
		final Object returnObject = joinPoint.proceed();
		this.postDeleteEvent.fire(auditedEntity);
		return returnObject;
	}
}