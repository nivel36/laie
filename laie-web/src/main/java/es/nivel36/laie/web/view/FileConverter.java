package es.nivel36.laie.web.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;

@FacesConverter(forClass = File.class, managed = true)
public class FileConverter implements Converter<File> {

	@Inject
	private FileService fileService;

	@Override
	public File getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			final Long id = Long.valueOf(value);
			return fileService.findById(id);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, File value) {
		if (value == null) {
			return null;
		}
		return value.getPhysicalFile().getAbsolutePath();
	}
}
