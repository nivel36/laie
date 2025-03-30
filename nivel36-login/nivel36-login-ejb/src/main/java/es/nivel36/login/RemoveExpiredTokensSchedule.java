package es.nivel36.login;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Schedule;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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

	private @Inject LoginTokenService loginTokenservice;

	/**
	 * Elimina los tokens de sesión que han expirado.
	 */
	@Schedule(persistent = false)
	public void removeExpiredTokens() {
		logger.info("Removing expired tokens");
		final List<LoginToken> loginTokens = this.loginTokenservice.findExpiredTokens();
		for (final LoginToken loginToken : loginTokens) {
			logger.trace("Removing expired token {}", loginToken);
			this.loginTokenservice.delete(loginToken);
		}
	}

	/**
	 * Inserta una instancia no nula de la clase de tipo <tt>LoginTokenService</tt>.
	 *
	 * @param loginTokenDao <tt>LoginTokenService</tt> no nula
	 */
	public void setLoginTokenService(final LoginTokenService loginTokenservice) {
		Objects.requireNonNull(loginTokenservice);
		this.loginTokenservice = loginTokenservice;
	}
}
