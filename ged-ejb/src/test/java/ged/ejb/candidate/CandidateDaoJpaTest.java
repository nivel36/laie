package ged.ejb.candidate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.job.offer.JobOffer;

@RunWith(MockitoJUnitRunner.class)
public class CandidateDaoJpaTest {

	private CandidateDaoJpa candidateJpaDao;

	@Mock
	private EntityManager entityManager;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void emailExistsNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateJpaDao.emailExists(null);
	}

	@Test
	public void emailExistsTest() {
		final Query mockedQuery = Mockito.mock(Query.class);
		Mockito.when(mockedQuery.getSingleResult()).thenReturn(Boolean.TRUE);
		Mockito.when(this.entityManager.createNamedQuery("Candidate.emailExists")).thenReturn(mockedQuery);
		this.candidateJpaDao.emailExists("aaron@test.com");
	}

	public void findAllByJobOfferEmptyTest() {
		Mockito.when(this.entityManager.createNamedQuery("Candidate.findAllByJobOffer"))
				.thenThrow(new NoResultException());
		final List<Candidate> candidates = this.candidateJpaDao.findAllByJobOffer(new JobOffer());
		Assert.assertNotNull(candidates);
		Assert.assertEquals(0, candidates.size());
	}

	public void findAllByJobOfferNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateJpaDao.findAllByJobOffer(null);
	}

	@Before
	public void setUp() {
		this.candidateJpaDao = new CandidateDaoJpa(this.entityManager);
	}
}
