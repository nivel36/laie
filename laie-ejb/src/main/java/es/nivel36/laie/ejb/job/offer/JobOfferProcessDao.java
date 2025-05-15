package es.nivel36.laie.ejb.job.offer;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class JobOfferProcessDao extends AbstractDao {

	public JobOfferProcess findJobOfferProcessByName(final String name) {
		Objects.requireNonNull(name);
		final String jpql = """
				SELECT j
				FROM JobOfferProcess j
				WHERE j.name = :name
				""";
		final TypedQuery<JobOfferProcess> query = this.em.createQuery(jpql, JobOfferProcess.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

	public List<JobOfferProcess> findJobOfferProcess() {
		final String jpql = """
				SELECT j
				FROM JobOfferProcess j
				""";
		final TypedQuery<JobOfferProcess> query = this.em.createQuery(jpql, JobOfferProcess.class);
		return query.getResultList();
	}
}
