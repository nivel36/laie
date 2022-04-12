package es.nivel36.laie.web.view;

import java.util.Objects;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.web.core.AbstractConverter;

@FacesConverter(forClass = File.class, managed = true)
public class FileConverter extends AbstractConverter<File> {

	@Inject
	private FileService fileService;

	@Override
	protected File getAsObject(Long id) {
		return fileService.findById(id);
	}

	public final void setFileService(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}
}
