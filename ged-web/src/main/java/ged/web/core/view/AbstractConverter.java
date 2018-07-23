package ged.web.core.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import ged.ejb.core.Service;
import ged.ejb.core.model.Identificable;

public abstract class AbstractConverter<T extends Identificable> implements Converter<T> {

	@Override
	public T getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		try {
			final long id = Long.parseLong(value);
			return this.getService().find(id);
		}
		catch (final NumberFormatException e) {
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

	protected abstract Service<T> getService();
}
