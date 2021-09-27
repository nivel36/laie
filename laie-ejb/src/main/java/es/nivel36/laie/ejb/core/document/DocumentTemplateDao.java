package es.nivel36.laie.ejb.core.document;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import javax.persistence.NoResultException;

import es.nivel36.laie.ejb.core.Language;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

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

}
