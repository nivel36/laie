package ged.ejb.core.document;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class DocumentDao extends AbstractDao<Document> {

	@Override
	protected Class<Document> getType() {
		return Document.class;
	}

	public Document findDocumentByName(final String name) {
		Objects.requireNonNull(name);
		return this.findByQuery(Document.class, "Document.findByName", map("name", name));
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}
