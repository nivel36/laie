package es.nivel36.laie.web.core.view;

import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import es.nivel36.laie.ejb.core.maintenance.AbstractEnumEntity;

public abstract class AbstractLookupEntityConverter<T extends AbstractEnumEntity> implements Converter<T> {

	protected ApplicationView getAppView() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{applicationView}", ApplicationView.class);
	}

	@Override
	public T getAsObject(final FacesContext context, final UIComponent component, final String value) {
		final List<T> elements = this.getListElements();
		for (final T element : elements) {
			if (element.getName().equals(value)) {
				return element;
			}
		}
		throw new NoSuchElementException(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final T value) {
		return value.toString();
	}

	protected abstract List<T> getListElements();
}
