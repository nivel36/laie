package ged.ejb.core.util;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log
@Interceptor
public class LoggingInterceptor implements Serializable {

	private static final long serialVersionUID = 1270828556944913397L;

	@AroundInvoke
	public Object log(final InvocationContext ctx) throws Exception {

		String className = ctx.getTarget().getClass().getName();
		// triming the proxy part
		className = className.substring(0, className.indexOf('$'));
		final String methodName = ctx.getMethod().getName();
		final Object[] params = ctx.getParameters();

		final Logger logger = LoggerFactory.getLogger(className);

		final Object returnMe;
		if (params.length == 0) {
			logger.trace(className, methodName);
			returnMe = ctx.proceed();
			logger.trace(className, methodName);
		} else if (params.length == 1) {
			logger.trace(className, methodName, params[0]);
			returnMe = ctx.proceed();
			logger.trace(className, methodName, params[0]);
		} else {
			logger.trace(className, methodName, params);
			returnMe = ctx.proceed();
			logger.trace(className, methodName, params);
		}
		return returnMe;
	}
}
