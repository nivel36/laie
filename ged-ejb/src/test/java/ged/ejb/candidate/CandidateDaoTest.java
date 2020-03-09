package ged.ejb.candidate;

import static ged.ejb.core.util.Parameters.map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.core.model.Page;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.job.offer.JobOffer;

@ExtendWith(MockitoExtension.class)
public class CandidateDaoTest {

	@Nested
	class EmailExist {

		@Test
		public void emailExistShouldReturnCandidate() {
			when(persistenceFacade.findByQuery(Boolean.class, "Candidate.emailExists", map("email", "aaron@test.com")))
					.thenReturn(Boolean.TRUE);

			final boolean result = candidateDao.emailExists("aaron@test.com");
			assertTrue(result);
		}

		@Test
		public void nullEmailShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateDao.emailExists(null);
			});
		}
	}

	@Nested
	class FindAllByJobOffer {

		@Test
		public void jobOfferWithCandidatesShouldReturnList() {
			final JobOffer jobOffer = new JobOffer();

			when(persistenceFacade.findByQuery(Candidate.class, "Candidate.findByJobOffer", map("jobOffer", jobOffer),
					Page.ALL_RESULTS)).thenReturn(mockCandidates());

			final List<Candidate> candidatesFromRepository = candidateDao.findCandidates(jobOffer, Page.ALL_RESULTS);
			assertEquals("aaron.douglas@test.com", candidatesFromRepository.get(0).getEmail());
		}

		@Test
		public void jobOfferWithoutCandidatesShouldReturnEmptyList() {
			final JobOffer jobOffer = new JobOffer();

			when(persistenceFacade.findByQuery(Candidate.class, "Candidate.findByJobOffer", map("jobOffer", jobOffer),
					Page.ALL_RESULTS)).thenThrow(new NoResultException());

			final List<Candidate> candidates = candidateDao.findCandidates(jobOffer, Page.ALL_RESULTS);
			assertEquals(0, candidates.size());
		}

		@Test
		public void nullJobOfferShouldReturnNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateDao.findCandidates(null, Page.ALL_RESULTS);
			});
		}
	}

	private CandidateDao candidateDao;

	@Mock
	private PersistenceFacade persistenceFacade;

	private Candidate mockCandidate() {
		final Candidate candidate = new Candidate();
		candidate.setName("Aaron");
		candidate.setSurname("Douglas");
		candidate.setEmail("aaron.douglas@test.com");
		return candidate;
	}

	private List<Candidate> mockCandidates() {
		final List<Candidate> candidates = new ArrayList<>();
		final Candidate candidate = this.mockCandidate();
		candidates.add(candidate);
		return candidates;
	}

	@BeforeEach
	public void setUp() {
		this.candidateDao = new CandidateDao();
		this.candidateDao.setPersistenceFacade(this.persistenceFacade);
	}
}
