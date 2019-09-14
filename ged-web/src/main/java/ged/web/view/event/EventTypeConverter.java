package ged.web.view.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import ged.ejb.event.EventType;

@FacesConverter(forClass = EventType.class)
public class EventTypeConverter implements Converter<EventType> {

	@Override
	public EventType getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		for (final EventType eventType : EventType.values()) {
			if (eventType.getName().equals(value)) {
				return eventType;
			}
		}
		throw new ConverterException("No event type wiht name " + value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final EventType value) {
		if (value == null) {
			return null;
		}
		return value.getName();
	}
}
