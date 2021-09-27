package es.nivel36.laie.web.core.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import es.nivel36.laie.ejb.core.AbstractService;
import es.nivel36.laie.ejb.core.model.AbstractEntity;

public abstract class AbstractConverter<T extends AbstractEntity> implements Converter<T> {

	@Override
	public T getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		try {
			final long id = Long.parseLong(value);
			return this.getService().find(id);
		} catch (final NumberFormatException e) {
			throw new ConverterException(value + " is not a valid id");
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final T value) {
		if (value == null) {
			return null;
		}
		return String.valueOf(value.getId());
	}

	protected abstract AbstractService<T> getService();
}
