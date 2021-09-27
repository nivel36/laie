package es.nivel36.laie.web.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.core.file.File;

@FacesConverter(forClass = File.class, managed = true)
public class FileConverter implements Converter<File> {

	@Override
	public File getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, File value) {
		if (value == null) {
			return null;
		}
		return value.getPhysicalFile().getAbsolutePath();
	}
}
