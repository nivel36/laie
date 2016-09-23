package ged.web.view.candidate;

import java.util.List;

import javax.faces.convert.FacesConverter;

import ged.ejb.core.FileType;
import ged.web.core.view.AbstractLookupEntityConverter;

@FacesConverter(forClass = FileType.class)
public class FileTypeConverter extends AbstractLookupEntityConverter<FileType> {

	@Override
	protected List<FileType> getListElements() {
		return getAppBean().getFileTypes();
	}
}