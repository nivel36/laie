package es.nivel36.laie.web.view;

import java.util.Objects;

import es.nivel36.laie.ejb.candidate.File;
import es.nivel36.laie.ejb.core.file.PhysicalFileService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(forClass = File.class, managed = true)
public class FileConverter extends AbstractConverter<File> {

	@Inject
	private PhysicalFileService fileService;

	@Override
	protected File getAsObject(Long id) {
		return fileService.findById(id);
	}

	public final void setFileService(final PhysicalFileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}
}
