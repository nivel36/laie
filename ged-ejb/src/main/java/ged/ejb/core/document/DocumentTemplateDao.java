package ged.ejb.core.document;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import ged.ejb.core.Language;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class DocumentTemplateDao extends AbstractDao<DocumentTemplate> {

	public DocumentTemplate findDocumentTemplateByName(final String name, final Language language) {
		Objects.requireNonNull(name);
		try {
			return this.findByQuery(DocumentTemplate.class, "DocumentTemplate.findByName",
					map("name", name).and("language", language.getCode()));
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	protected Class<DocumentTemplate> getType() {
		return DocumentTemplate.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}
