package es.nivel36.laie.ejb.core.action;

import java.util.Objects;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Audited(action = ActionType.UPDATE)
public class UpdateInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(UpdateInterceptor.class);

	private final Event<Auditable> updateEvent;

	@Inject
	public UpdateInterceptor(@Update final Event<Auditable> updateEvent) {
		Objects.requireNonNull(updateEvent);
		this.updateEvent = updateEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Auditable auditedEntity = getAuditedEntity(joinPoint);
		logger.trace("Update event fired");
		final Object returnObject = joinPoint.proceed();
		this.updateEvent.fire(auditedEntity);
		return returnObject;
	}
}