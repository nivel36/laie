package ged.ejb.core.document;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class DocumentTemplateDao extends AbstractDao<DocumentTemplate> {

	@Override
	protected Class<DocumentTemplate> getType() {
		return DocumentTemplate.class;
	}

	public DocumentTemplate findDocumentTemplateByName(final String name) {
		Objects.requireNonNull(name);
		return this.findByQuery(DocumentTemplate.class, "DocumentTemplate.findByName", map("name", name));
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}
