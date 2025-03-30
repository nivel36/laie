package es.nivel36.laie.ejb.candidate;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;
import jakarta.persistence.Query;

public class FileDao extends AbstractDao {

	public List<File> findFilesByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String namedQuery = "File.findByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(File.class, namedQuery, parameters, page);
	}

	public long countFilesByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		final String namedQuery = "File.countByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}

	public int deleteFile(final File file) {
		Objects.requireNonNull(file);
		final String namedQuery = "File.delete";
		final Query query = this.em.createNamedQuery(namedQuery);
		query.setParameter("file", file);
		return query.executeUpdate();
	}
}