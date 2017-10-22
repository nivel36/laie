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
import ged.ejb.user.User;

@Interceptor
@Audited(action = ActionType.LOGIN)
public class LoginInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final Event<User> postLoginEvent;

	private final Event<User> preLoginEvent;

	@Inject
	public LoginInterceptor(@PostPersist final Event<User> postLoginEvent,
			@PrePersist final Event<User> preLoginEvent) {
		Objects.requireNonNull(postLoginEvent);
		Objects.requireNonNull(preLoginEvent);
		this.postLoginEvent = postLoginEvent;
		this.preLoginEvent = preLoginEvent;
	}

	@AroundInvoke
	public Object fireEvent(final InvocationContext joinPoint) throws Exception {
		final User user = getUserEntity(joinPoint);
		logger.trace("Loign event fired");
		this.preLoginEvent.fire(user);
		final Object returnObject = joinPoint.proceed();
		this.postLoginEvent.fire(user);
		return returnObject;
	}

	protected User getUserEntity(final InvocationContext joinPoint) {
		final Object[] parameters = joinPoint.getParameters();
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Arguments: " + parameters.length);
		}
		final Object entityObject = parameters[0];
		if (!(entityObject instanceof User)) {
			throw new IllegalArgumentException("Not user entity");
		}
		return (User) entityObject;
	}
}