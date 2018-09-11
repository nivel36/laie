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

@Interceptor
@Audited(action = ActionType.LOGIN)
public class LoginInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final Event<String> postLoginEvent;

	@Inject
	public LoginInterceptor(@PostLogin final Event<String> postLoginEvent) {
		Objects.requireNonNull(postLoginEvent);
		this.postLoginEvent = postLoginEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		logger.trace("Loign event fired");
		final String email = getEmail(joinPoint);
		final Object returnObject = joinPoint.proceed();
		this.postLoginEvent.fire(email);
		return returnObject;
	}

	protected String getEmail(final InvocationContext joinPoint) {
		final Object[] parameters = joinPoint.getParameters();
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof String)) {
			throw new IllegalArgumentException("Not user entity");
		}
		return (String) entityObject;
	}
}