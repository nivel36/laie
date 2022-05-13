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
@Audited(action = ActionType.CREATE)
public class CreateInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(CreateInterceptor.class);

	private final Event<Auditable> createEvent;

	@Inject
	public CreateInterceptor(@Create final Event<Auditable> createEvent) {
		Objects.requireNonNull(createEvent);
		this.createEvent = createEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final Auditable auditedEntity = getAuditedEntity(joinPoint);
		logger.trace("Create event fired");
		final Object returnObject = joinPoint.proceed();
		this.createEvent.fire(auditedEntity);
		return returnObject;
	}
}