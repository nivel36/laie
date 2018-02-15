package ged.ejb.candidate;

import static ged.ejb.core.model.QueryParameter.with;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@RunWith(MockitoJUnitRunner.class)
public class CandidateDaoJpaTest {

	private CandidateDaoJpa candidateDaoJpa;

	@Mock
	private PersistenceFacade persistenceFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void emailExistsNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateDaoJpa.emailExists(null);
	}

	@Test
	public void emailExistsTest() {
		Mockito.when(this.persistenceFacade.findByQuery(Boolean.class, "Candidate.emailExists",
				with("email", "aaron@test.com").parameters())).thenReturn(Boolean.TRUE);
		final boolean result = this.candidateDaoJpa.emailExists("aaron@test.com");
		Assert.assertTrue(result);
	}

	@Test
	public void findAllByJobOfferEmptyTest() {
		final JobOffer jobOffer = Mockito.mock(JobOffer.class);
		Mockito.when(this.persistenceFacade.findByQuery(Candidate.class, "Candidate.findAllByJobOffer",
				with("jobOffer", jobOffer).parameters())).thenThrow(new NoResultException());
		final List<Candidate> candidates = this.candidateDaoJpa.findAllByJobOffer(jobOffer);
		Assert.assertEquals(0, candidates.size());
	}

	@Test
	public void findAllByJobOfferNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateDaoJpa.findAllByJobOffer(null);
	}

	@Test
	public void findAllByJobOfferTest() {
		final JobOffer jobOffer = Mockito.mock(JobOffer.class);
		Mockito.when(this.persistenceFacade.findByQuery(Candidate.class, "Candidate.findAllByJobOffer",
				with("jobOffer", jobOffer).parameters(), 0, 0)).thenReturn(mockCandidates());
		final List<Candidate> candidatesFromRepository = this.candidateDaoJpa.findAllByJobOffer(jobOffer);
		Assert.assertEquals(1, candidatesFromRepository.size());
		Assert.assertEquals("aaron.douglas@test.com", candidatesFromRepository.get(0).getEmail());
	}

	@Test
	public void findAllTagsTest() {
		Mockito.when(this.persistenceFacade.findAll(Tag.class)).thenReturn(new ArrayList<Tag>());
		final List<Tag> tagsFromRepository = this.candidateDaoJpa.findAllTags();
		Assert.assertNotNull(tagsFromRepository);
		Assert.assertEquals(0, tagsFromRepository.size());
	}

	@Test
	public void findCandidateAndFilesByWrongIdTest() {
		this.thrown.expect(IllegalArgumentException.class);
		this.candidateDaoJpa.findCandidateAndFiles(0);
	}

	@Test
	public void findCandidateAndFilesTest() {
		final Candidate mockedCandidate = mockCandidate();
		Mockito.when(this.persistenceFacade.findByQuery(Candidate.class, "Candidate.findCandidateAndFilesById",
				with("id", 1L).parameters())).thenReturn(mockedCandidate);
		final Candidate candidateFromRepository = this.candidateDaoJpa.findCandidateAndFiles(1L);
		Assert.assertEquals(mockedCandidate, candidateFromRepository);
	}

	private Candidate mockCandidate() {
		final Candidate candidate = new Candidate();
		candidate.setName("Aaron");
		candidate.setSurname("Douglas");
		candidate.setEmail("aaron.douglas@test.com");
		return candidate;
	}

	private List<Candidate> mockCandidates() {
		final List<Candidate> candidates = new ArrayList<>();
		final Candidate candidate = mockCandidate();
		candidates.add(candidate);
		return candidates;
	}

	@Before
	public void setUp() {
		this.candidateDaoJpa = new CandidateDaoJpa();
		this.candidateDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}
}
