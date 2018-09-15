package ged.ejb.core.security;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ged.ejb.core.model.Ownerable;

@Securized
@Interceptor
public class SecurityInterceptor {

	@Inject
	private SecurityContext securityContext;

	@AroundInvoke
	public Object checkSecurity(final InvocationContext joinPoint) throws Exception {
		final Object[] parameters = getParameters(joinPoint);
		final Ownerable[] ownerableEntities = getOwnerableEntitiesFromParameters(parameters);
		for (final Ownerable entity : ownerableEntities) {
			if (!securityContext.canEdit(entity)) {
				throw new SecurityException();
			}
		}
		return joinPoint.proceed();
	}

	private Ownerable[] getOwnerableEntitiesFromParameters(final Object[] parameters) {
		final int parametersLength = parameters.length;
		final Ownerable[] ownerableEntities = new Ownerable[parametersLength];

		int numberOfOwnerableFound = 0;
		for (int i = 0; i < parametersLength; i++) {
			final Object object = parameters[i];
			if (object instanceof Ownerable) {
				ownerableEntities[numberOfOwnerableFound] = (Ownerable) object;
				numberOfOwnerableFound++;
			}
		}
		if (numberOfOwnerableFound == 0) {
			throw new IllegalArgumentException("No ownerable entities");
		}
		return trimArray(parametersLength, ownerableEntities);
	}

	private Object[] getParameters(final InvocationContext joinPoint) {
		return joinPoint.getParameters();
	}

	public void setSecurityContext(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	private Ownerable[] trimArray(final int length, final Ownerable[] untrimmedOwnerableEntities) {
		if (untrimmedOwnerableEntities.length == length) {
			return untrimmedOwnerableEntities;
		}
		final Ownerable[] trimmedOwnerableEntities = new Ownerable[length];
		System.arraycopy(untrimmedOwnerableEntities, 0, trimmedOwnerableEntities, 0, length);
		return trimmedOwnerableEntities;
	}
}
