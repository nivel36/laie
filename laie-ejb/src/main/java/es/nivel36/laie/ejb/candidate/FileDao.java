package es.nivel36.laie.ejb.candidate;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class FileDao extends AbstractDao {

	public List<CandidateFileAttachment> findFilesByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT f
				FROM CandidateFileAttachment f
				WHERE f.candidate = :candidate
				""";
		final TypedQuery<CandidateFileAttachment> query = this.em.createQuery(jpql, CandidateFileAttachment.class);
		query.setParameter("candidate", candidate);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countFilesByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		final String jpql = """
				SELECT COUNT(f)
				FROM CandidateFileAttachment f
				WHERE f.candidate = :candidate
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("candidate", candidate);
		return query.getSingleResult();
	}

	public int deleteFile(final CandidateFileAttachment file) {
		Objects.requireNonNull(file);
		final String jpql = """
				DELETE 
				FROM CandidateFileAttachment f 
				WHERE f = :file
				""";
		final Query query = this.em.createQuery(jpql);
		query.setParameter("file", file);
		return query.executeUpdate();
	}
}