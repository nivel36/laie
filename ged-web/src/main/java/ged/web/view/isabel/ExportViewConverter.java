package ged.web.view.isabel;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter("exportViewConverter")
public class ExportViewConverter implements Converter<ExportViewItemI> {

	@Override
	public ExportViewItemI getAsObject(final FacesContext context, final UIComponent component, final String id) {
		try {
			@SuppressWarnings("unchecked")
			final DualListModel<ExportViewItemI> dualList = (DualListModel<ExportViewItemI>) ((PickList) component).getValue();
			ExportViewItemI item = getObjectFromList(dualList.getSource(), id);
			if (item == null) {
				item = getObjectFromList(dualList.getTarget(), id);
			}
			return item;
		} catch (ClassCastException cce) {
			throw new ConverterException();
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final ExportViewItemI value) {
		if (value == null) {
			return "";
		} else {
			return value.getLabel();
		}
	}
	
	private ExportViewItemI getObjectFromList(final List<?> list, final String id) {
		for (final Object item: list) {
			final ExportViewItemI viewItem = (ExportViewItemI) item;
			if (viewItem.getLabel().equals(id)) {
				return viewItem;
			}
		}
		return null;
	}
}
