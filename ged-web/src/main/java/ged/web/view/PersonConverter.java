package ged.web.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ged.ejb.person.Person;

@FacesConverter(managed = true, forClass = Person.class)
public class PersonConverter implements Converter<Person>{

	@Override
	public Person getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Person value) {
		if (value == null) {
			return null;
		}
		return String.valueOf(value.getId());
	}
}
