package es.nivel36.laie.web.core;

public interface Permission<T> {
	
	boolean validate(T entity);
}
