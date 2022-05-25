package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.user.User;

public interface Permission<T> {
	
	boolean validate(T entity, User user);
}
