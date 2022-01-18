package es.nivel36.laie.ejb.core.document;

import es.nivel36.laie.ejb.core.model.Merger;

public class DocumentTemplateMerger implements Merger<DocumentTemplate, DocumentTemplateDto> {

	@Override
	public void merge(DocumentTemplate entity, DocumentTemplateDto dto) {
		entity.setLanguage(dto.getLanguage());
		entity.setName(dto.getName());
		entity.setText(dto.getText());
		entity.setTitle(dto.getTitle());
		entity.setUid(dto.getUid());
	}
}
