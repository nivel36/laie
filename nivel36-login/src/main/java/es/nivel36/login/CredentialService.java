package es.nivel36.login;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import es.nivel36.login.LoginToken.TokenType;

@Stateless
public class CredentialService {

	private @Inject EntityManager em;

	public Credential findUserByTokenHashAndType(final byte[] tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		final String namedQuery = "User.findByTokenHashAndType";
		final TypedQuery<Credential> query = em.createNamedQuery(namedQuery, Credential.class);
		query.setParameter("tokenHash", tokenHash);
		query.setParameter("type", type);
		return query.getSingleResult();
	}
	
	public Credential findCredential(final String email) {
		Objects.requireNonNull(email);
		final String namedQuery = "User.findCredential";
		final TypedQuery<Credential> query = em.createNamedQuery(namedQuery, Credential.class);
		query.setParameter(email, "email");
		return query.getSingleResult();
	}
}
