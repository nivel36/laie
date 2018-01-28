package ged.ejb.candidate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
		mockQuery("Candidate.emailExists", Boolean.TRUE, Boolean.class);

		final boolean result = this.candidateJpaDao.emailExists("aaron@test.com");

		Assert.assertTrue(result);
	}

	@Test
	public void findAllByJobOfferEmptyTest() {
		Mockito.when(this.entityManager.createNamedQuery("Candidate.findAllByJobOffer", Candidate.class))
				.thenThrow(new NoResultException());

		final List<Candidate> candidates = this.candidateJpaDao.findAllByJobOffer(new JobOffer());

		Assert.assertEquals(0, candidates.size());
	}

	@Test
	public void findAllByJobOfferNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateJpaDao.findAllByJobOffer(null);
	}

	@Test
	public void findAllByJobOfferTest() {
		final List<Candidate> candidates = mockCandidates();
		mockQuery("Candidate.findAllByJobOffer", candidates, Candidate.class);

		final List<Candidate> candidatesFromRepository = this.candidateJpaDao.findAllByJobOffer(new JobOffer());

		Assert.assertEquals(1, candidatesFromRepository.size());
		Assert.assertEquals("aaron.douglas@test.com", candidatesFromRepository.get(0).getEmail());
	}

	@Test
	public void findCandidateAndFilesByWrongIdTest() {
		this.thrown.expect(IllegalArgumentException.class);
		this.candidateJpaDao.findCandidateAndFiles(0);
	}

	@Test
	public void findCandidateAndFilesTest() {
		final Candidate mockedCandidate = mockCandidate();
		mockQuery("Candidate.findCandidateAndFilesById", mockedCandidate, Candidate.class);

		final Candidate candidateFromRepository = this.candidateJpaDao.findCandidateAndFiles(1);

		Assert.assertEquals(mockedCandidate, candidateFromRepository);
	}

	private Candidate mockCandidate() {
		final Candidate candidate = new Candidate();
		candidate.setName("Aaron");
		candidate.setSurename("Douglas");
		candidate.setEmail("aaron.douglas@test.com");
		return candidate;
	}

	private List<Candidate> mockCandidates() {
		final List<Candidate> candidates = new ArrayList<>();
		final Candidate candidate = mockCandidate();
		candidates.add(candidate);
		return candidates;
	}

	private <T> void mockQuery(final String query, final List<T> mockResults, final Class<T> type) {
		@SuppressWarnings("unchecked")
		final TypedQuery<T> mockedQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockedQuery.getResultList()).thenReturn(mockResults);
		Mockito.when(this.entityManager.createNamedQuery(query, type)).thenReturn(mockedQuery);
	}

	private <T> void mockQuery(final String query, final T mockResult, final Class<T> type) {
		@SuppressWarnings("unchecked")
		final TypedQuery<T> mockedQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockedQuery.getSingleResult()).thenReturn(mockResult);
		Mockito.when(this.entityManager.createNamedQuery(query, type)).thenReturn(mockedQuery);
	}

	@Before
	public void setUp() {
		this.candidateJpaDao = new CandidateDaoJpa(this.entityManager);
	}
}
