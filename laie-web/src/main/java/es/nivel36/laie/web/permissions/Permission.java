package es.nivel36.laie.web.permissions;

public interface Permission<T> {
	
	boolean validate(T entity);
}
