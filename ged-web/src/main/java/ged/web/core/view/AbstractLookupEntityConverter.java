package ged.web.core.view;

import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ged.ejb.core.maintenance.AbstractEnumEntity;

public abstract class AbstractLookupEntityConverter<T extends AbstractEnumEntity> implements Converter {

	protected ApplicationBean getAppBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{applicationBean}", ApplicationBean.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<T> elements = getListElements();
		for (T element : elements) {
			if (element.getName().equals(value)) {
				return element;
			}
		}
		throw new NoSuchElementException(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

	protected abstract List<T> getListElements();
}
