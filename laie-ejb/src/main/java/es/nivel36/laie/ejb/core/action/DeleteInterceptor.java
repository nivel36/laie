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
@Audited(action = ActionType.DELETE)
public class DeleteInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(DeleteInterceptor.class);

	private final Event<Auditable> deleteInterceptor;

	@Inject
	public DeleteInterceptor(@Delete final Event<Auditable> deleteEvent) {
		Objects.requireNonNull(deleteEvent);
		this.deleteInterceptor = deleteEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Auditable auditedEntity = getAuditedEntity(joinPoint);
		logger.trace("Delete event fired");
		final Object returnObject = joinPoint.proceed();
		this.deleteInterceptor.fire(auditedEntity);
		return returnObject;
	}
}
