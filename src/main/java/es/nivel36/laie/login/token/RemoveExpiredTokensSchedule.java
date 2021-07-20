package es.nivel36.laie.login.token;

import java.util.List;
import java.util.Objects;

import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.service.Repository;

@Startup
@Singleton
public class RemoveExpiredTokensSchedule {

	private static final Logger logger = LoggerFactory.getLogger(RemoveExpiredTokensSchedule.class);

	@Repository
	@Inject
	private LoginTokenDao loginTokenDao;

	@Schedule(persistent = false)
	public void removeExpiredTokens() {
		logger.info("Removing expired tokens");
		//TODO: do pagination
		final List<LoginToken> loginTokens = this.loginTokenDao.findExpiredTokens(0, 1000);
		for (final LoginToken loginToken : loginTokens) {
			logger.trace("Removing expired token {}", loginToken);
			this.loginTokenDao.delete(loginToken);
		}
	}

	public void setLoginTokenDao(final LoginTokenDao loginTokenDao) {
		Objects.requireNonNull(loginTokenDao);
		this.loginTokenDao = loginTokenDao;
	}
}
