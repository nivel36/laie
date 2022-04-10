package es.nivel36.laie.web.core;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import es.nivel36.laie.ejb.core.model.Identifiable;

public abstract class AbstractConverter<T extends Identifiable> implements Converter<T> {

	@Override
	public T getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		final Long id = Long.valueOf(value);
		return getAsObject(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, T value) {
		if (value == null) {
			return null;
		}
		final Long id = value.getId();
		return String.valueOf(id);
	}

	protected abstract T getAsObject(Long id);
}
