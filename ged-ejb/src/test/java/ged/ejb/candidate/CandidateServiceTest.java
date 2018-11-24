package ged.ejb.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import ged.ejb.core.file.ServerFile;
import ged.ejb.core.file.ServerFileDao;
import ged.ejb.job.offer.JobCandidatureDao;
import ged.ejb.job.offer.JobOffer;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

	@Nested
	class AddFileToCandidate {

		@Test
		public void nullParametersShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.addFileToCandidate(null, null);
			});
		}

		@Test
		public void shouldBeOk() {
			final ServerFile mockedUploadedServerFile = mock(ServerFile.class);

			final Candidate mockedCandidate = mock(Candidate.class);
			candidateService.addFileToCandidate(mockedCandidate, mockedUploadedServerFile);
		}
	}

	@Nested
	class FindAllByJobOffer {

		@Test
		public void findAllByJobOfferTest() {
			final JobOffer jobOffer = new JobOffer();

			when(candidateDao.findCandidates(jobOffer)).thenReturn(new ArrayList<>());

			final List<Candidate> candidatesFromRepository = candidateService.findCandidates(jobOffer);
			assertEquals(0, candidatesFromRepository.size());
		}

		@Test
		public void nullJobOfferShouldReturnNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.findCandidates(null);
			});
		}
	}

	@Nested
	class FindAllCandidateDataByCandidateId {

		@Test
		public void badIdShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				candidateService.findCandidateData(0);
			});
		}

		@Test
		public void validIdShouldReturnCandidate() {
			final Candidate mockedCandidate = mock(Candidate.class);

			when(candidateDao.findCandidateData(1L)).thenReturn(mockedCandidate);

			final Candidate candidateFromRepository = candidateService.findCandidateData(1L);
			assertNotNull(candidateFromRepository);
		}
	}

	@Nested
	class FindFileByFileId {

		@Test
		public void badIdShouldThrowIllegalArgumentExpcetion() {
			assertThrows(IllegalArgumentException.class, () -> {
				candidateService.findFile(0);
			});
		}

		@Test
		public void findFileTest() {
			final ServerFile mockedUploadedServerFile = mock(ServerFile.class);

			when(serverFileDao.find(1L)).thenReturn(mockedUploadedServerFile);

			final ServerFile serverFileFromRepository = candidateService.findFile(1L);
			assertEquals(mockedUploadedServerFile, serverFileFromRepository);
		}
	}

	@Nested
	class FindLastAddedCandidates {

		@Test
		public void badNumberShouldThrowIllegalArgumentExpcetion() {
			assertThrows(IllegalArgumentException.class, () -> {
				candidateService.findLastAddedCandidates(-1);
			});
		}

		@Test
		public void validNumberShouldReturnListOfCandidates() {
			when(candidateDao.findLastAddedCandidates(1)).thenReturn(new ArrayList<>());

			final List<Candidate> candidatesFromRepository = candidateService.findLastAddedCandidates(1);
			assertEquals(0, candidatesFromRepository.size());
		}
	}

	@Nested
	class FindNumberOfCandidates {

		@Test
		public void shouldReturnNumberOfCandidates() {
			when(candidateDao.findNumberOfCandidates()).thenReturn(1L);

			final long numberOfCandidates = candidateService.findNumberOfCandidates();
			assertEquals(1, numberOfCandidates);
		}
	}

	@Nested
	class UpdateFile {

		@Test
		public void nullServerFileShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.updateFile(null);
			});
		}

		@Test
		public void updateFileTest() {
			final ServerFile mockedServerFile = mock(ServerFile.class);

			when(serverFileDao.save(mockedServerFile)).thenReturn(mockedServerFile);

			final ServerFile updatedServerFile = candidateService.updateFile(mockedServerFile);
			assertEquals(mockedServerFile, updatedServerFile);
		}
	}

	@Mock
	private CandidateDao candidateDao;

	private CandidateService candidateService;

	@Mock
	private JobCandidatureDao jobCandidatureDao;

	@Mock
	private ServerFileDao serverFileDao;

	@BeforeEach
	public void setUp() {
		this.candidateService = new CandidateService();
		this.candidateService.setCandidateDao(this.candidateDao);
		this.candidateService.setJobCandidatureDao(this.jobCandidatureDao);
		this.candidateService.setServerFileDao(this.serverFileDao);
	}
}