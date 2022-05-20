package es.nivel36.login;

import java.util.List;
import java.util.Objects;

import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Elimina todos los tokens de sesión expirados de la base de datos </br>
 * Cada día a las doce de la noche la clase busca los tokens y los elimina.</br>
 * Esta clase se instancia al levantar la aplicación.
 * </p>
 */
@Startup
@Singleton
public class RemoveExpiredTokensSchedule {

	private static final Logger logger = LoggerFactory.getLogger(RemoveExpiredTokensSchedule.class);

	private @Inject LoginTokenDao loginTokenDao;

	/**
	 * Elimina los tokens de sesión que han expirado.
	 */
	@Schedule(persistent = false)
	public void removeExpiredTokens() {
		logger.info("Removing expired tokens");
		final List<LoginToken> loginTokens = this.loginTokenDao.findExpiredTokens();
		for (final LoginToken loginToken : loginTokens) {
			logger.trace("Removing expired token {}", loginToken);
			this.loginTokenDao.delete(loginToken);
		}
	}

	/**
	 * Inserta una instancia no nula de la clase de tipo <tt>LoginTokenDao</tt>.
	 * 
	 * @param loginTokenDao <tt>LoginTokenDao</tt> no nula
	 */
	public void setLoginTokenDao(final LoginTokenDao loginTokenDao) {
		Objects.requireNonNull(loginTokenDao);
		this.loginTokenDao = loginTokenDao;
	}
}
