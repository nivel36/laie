package es.nivel36.laie.ejb.core.document;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.Language;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.UidGenerator;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class DocumentTemplateDao extends AbstractDao {
	
	public void insert(final DocumentTemplate documentTemplate) {
		Objects.requireNonNull(documentTemplate);
		this.setUid(null);
		this.em.persist(documentTemplate);
	}

	private void setUid(final DocumentTemplate documentTemplate) {
		String uid;
		do {
			uid = UidGenerator.generate(DocumentTemplate.class);
			documentTemplate.setUid(uid);
		} while (!this.checkDuplicateUid(uid));
	}

	private boolean checkDuplicateUid(final String uid) {
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Boolean.class, "DocumentTemplate.checkDuplicateUid", parameters);
	}
	
	public DocumentTemplate findDocumentTemplateByName(final String name, final Language language) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(language);
		final String namedQuery = "DocumentTemplate.findByName";
		final String code = language.getCode();
		final Parameters parameters = map("name", name).and("language", code);
		return this.findByQuery(DocumentTemplate.class, namedQuery, parameters);
	}
}