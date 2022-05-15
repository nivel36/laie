package es.nivel36.laie.ejb.core.document;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.Language;
import es.nivel36.laie.ejb.core.model.AbstractDao;

import es.nivel36.laie.ejb.core.util.Parameters;


public class DocumentTemplateDao extends AbstractDao {
	
	public DocumentTemplate findDocumentTemplateByName(final String name, final Language language) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(language);
		final String namedQuery = "DocumentTemplate.findByName";
		final String code = language.getCode();
		final Parameters parameters = map("name", name).and("language", code);
		return this.findByQuery(DocumentTemplate.class, namedQuery, parameters);
	}
}