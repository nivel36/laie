package es.nivel36.laie.ejb.core.document;

import es.nivel36.laie.ejb.core.Mapper;

public class DocumentTemplateMapper implements Mapper<DocumentTemplate, DocumentTemplateDto> {

	@Override
	public DocumentTemplateDto map(final DocumentTemplate entity) {
		final DocumentTemplateDto dto = new DocumentTemplateDto();
		dto.setLanguage(entity.getLanguage());
		dto.setName(entity.getName());
		dto.setText(entity.getText());
		dto.setTitle(entity.getTitle());
		dto.setUid(entity.getUid());
		return dto;
	}
}
