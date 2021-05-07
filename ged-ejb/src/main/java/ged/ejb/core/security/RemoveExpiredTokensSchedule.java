package ged.ejb.core.security;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Repository;

@Startup
@Singleton
public class RemoveExpiredTokensSchedule {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Repository
	@Inject
	private LoginTokenDao loginTokenDao;

	@Schedule(persistent = false)
	public void removeExpiredTokens() {
		logger.info("Removing expired tokens");
		final List<LoginToken> loginTokens = this.loginTokenDao.findExpiredTokens();
		for (final LoginToken loginToken : loginTokens) {
			logger.trace("Removing expired token {}", loginToken);
			this.loginTokenDao.delete(loginToken);
		}
	}

	public void setLoginTokenDao(LoginTokenDao loginTokenDao) {
		this.loginTokenDao = loginTokenDao;
	}
}
