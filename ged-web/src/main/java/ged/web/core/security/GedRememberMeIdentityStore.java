package ged.web.core.security;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;

import ged.ejb.core.security.GedIdentityStore;
import ged.ejb.core.security.LoginToken.TokenType;
import ged.ejb.core.security.LoginTokenService;
import ged.ejb.user.Credential;
import ged.ejb.user.UserService;

@ApplicationScoped
public class GedRememberMeIdentityStore implements RememberMeIdentityStore {
	
	@Inject
	private HttpServletRequest request;

	@Inject
	private UserService userService;

	@Inject
	private LoginTokenService loginTokenService;
	
	@Inject
	private GedIdentityStore gedIdentityStore;

	@Override
	public CredentialValidationResult validate(RememberMeCredential credential) {
		return gedIdentityStore.validate(userService.findUserByTokenHash(credential.getToken()));
	}

	@Override
	public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
		String ipAddress = request.getRemoteAddr();
		String description = "Remember me session for " + ipAddress + " on " + request.getHeader("User-Agent");
		return loginTokenService.generate(callerPrincipal.getName(), ipAddress, description, TokenType.REMEMBER_ME);
	}

	@Override
	public void removeLoginToken(String loginToken) {
		loginTokenService.remove(loginToken);
	}
}
