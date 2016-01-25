package ged.ejb.core.util;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class LoggingInterceptor implements Serializable {

	private static final long serialVersionUID = 1270828556944913397L;

	@AroundInvoke
	public Object log(InvocationContext ctx) throws Exception {
		
		String className = ctx.getTarget().getClass().getName();
		// triming the proxy part
		className = className.substring(0, className.indexOf("$"));
		String methodName = ctx.getMethod().getName();
		Object[] params = ctx.getParameters();
		
		Logger logger = Logger.getLogger(className);

		Object returnMe = null;
		if (params.length == 0) {
			logger.entering(className, methodName);
			returnMe = ctx.proceed();
			logger.exiting(className, methodName);
		} else if (params.length == 1) {
			logger.entering(className, methodName, params[0]);
			returnMe = ctx.proceed();
			logger.exiting(className, methodName, params[0]);
		} else {
			logger.entering(className, methodName, params);
			returnMe = ctx.proceed();
			logger.exiting(className, methodName, params);
		}
		return returnMe;
	}
}
