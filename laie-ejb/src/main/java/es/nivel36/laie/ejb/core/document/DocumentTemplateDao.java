package es.nivel36.laie.ejb.core.document;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.Language;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class DocumentTemplateDao extends AbstractDao {

	public DocumentTemplate findDocumentTemplateByNameAndLanguage(final String name, final Language language) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(language);
		final String jpql = """
				SELECT d 
				FROM DocumentTemplate d 
				WHERE d.name = :name
				AND d.language = :language
				""";
		final TypedQuery<DocumentTemplate> query = this.em.createQuery(jpql, DocumentTemplate.class);
		query.setParameter("name",name);
		query.setParameter("language",language);
		return query.getSingleResult();
	}

	public List<DocumentTemplate> findDocumentTemplateByName(final String name) {
		Objects.requireNonNull(name);
		final String jpql = """
				SELECT d 
				FROM DocumentTemplate d 
				WHERE d.name = :name
				""";
		final TypedQuery<DocumentTemplate> query = this.em.createQuery(jpql, DocumentTemplate.class);
		query.setParameter("name",name);
		return query.getResultList();
	}
}