package ged.ejb.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.core.file.File;
import ged.ejb.core.file.FileJpaDao;
import ged.ejb.core.model.Page;
import ged.ejb.job.candidature.JobCandidatureDao;
import ged.ejb.job.offer.JobOffer;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {


	@Nested
	class FindAllByJobOffer {

		@Test
		public void findAllByJobOfferTest() {
			final JobOffer jobOffer = new JobOffer();

			when(candidateDao.findCandidates(jobOffer, Page.ALL_RESULTS)).thenReturn(new ArrayList<>());

			final List<Candidate> candidatesFromRepository = candidateService.findByJobOffer(jobOffer,
					Page.ALL_RESULTS);
			assertEquals(0, candidatesFromRepository.size());
		}

		@Test
		public void nullJobOfferShouldReturnNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.findByJobOffer(null, Page.ALL_RESULTS);
			});
		}
	}

	@Mock
	private CandidateDao candidateDao;

	private CandidateService candidateService;

	@Mock
	private JobCandidatureDao jobCandidatureDao;

	@Mock
	private FileJpaDao serverFileDao;

	@BeforeEach
	public void setUp() {
		this.candidateService = new CandidateService();
		this.candidateService.setCandidateDao(this.candidateDao);

	}
}