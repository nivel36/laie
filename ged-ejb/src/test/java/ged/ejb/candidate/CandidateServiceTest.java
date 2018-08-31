package ged.ejb.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

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

	@Mock
	private CandidateDao candidateDao;

	private CandidateService candidateService;

	@Mock
	private JobCandidatureDao jobCandidatureDao;

	@Mock
	private ServerFileDao serverFileDao;

	@Nested
	class FindAllByJobOffer {

		@Test
		public void nullJobOfferShouldReturnNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.findCandidatesByJobOffer(null);
			});
		}

		@Test
		public void findAllByJobOfferTest() {
			final JobOffer jobOffer = new JobOffer();

			when(candidateDao.findCandidatesByJobOffer(jobOffer)).thenReturn(new ArrayList<>());

			final List<Candidate> candidatesFromRepository = candidateService.findCandidatesByJobOffer(jobOffer);
			assertEquals(0, candidatesFromRepository.size());
		}
	}

	@Nested
	class FindAllCandidateDataByCandidateId {

		@Test
		public void badIdShouldThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> {
				candidateService.findAllCandidateDataByCandidateId(0);
			});
		}

		@Test
		public void validIdShouldReturnCandidate() {
			final Candidate mockedCandidate = mock(Candidate.class);

			when(candidateDao.findAllCandidateDataById(1L)).thenReturn(mockedCandidate);

			final Candidate candidateFromRepository = candidateService.findAllCandidateDataByCandidateId(1L);
			assertNotNull(candidateFromRepository);
		}
	}

	@Nested
	class FindFileByFileId {

		@Test
		public void badIdShouldThrowIllegalArgumentExpcetion() {
			assertThrows(IllegalArgumentException.class, () -> {
				candidateService.findFileByFileId(0);
			});
		}

		@Test
		public void findFileTest() {
			final ServerFile mockedUploadedServerFile = mock(ServerFile.class);

			when(serverFileDao.find(1L)).thenReturn(mockedUploadedServerFile);

			final ServerFile serverFileFromRepository = candidateService.findFileByFileId(1L);
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
	class Save {

		@Test
		public void updateCandidateShouldReturnSavedCandidate() {
			final Candidate candidate = new Candidate();
			candidate.setEmail("aaron@test.com");
			candidate.setId(1L);

			when(candidateDao.find(candidate.getId())).thenReturn(candidate);
			when(candidateDao.save(candidate)).thenReturn(candidate);

			final Candidate savedCandidate = candidateService.save(candidate);
			assertEquals(candidate, savedCandidate);
		}

		@Test
		public void updateCandidateWhithDuplicatedEmailShouldThrowValidationException() {
			assertThrows(ValidationException.class, () -> {
				final Candidate candidate = new Candidate();
				candidate.setEmail("aaron@test.com");
				candidate.setId(1L);

				when(candidateDao.emailExists(candidate.getEmail())).thenReturn(true);
				when(candidateDao.find(candidate.getId())).thenReturn(mock(Candidate.class));
				
				candidateService.save(candidate);
			});
		}

		@Test
		public void nullCandidateShouldReturnNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.save(null);
			});
		}

		@Test
		public void insertNewCandidateShouldReturnSavedCandidate() {
			final Candidate candidate = new Candidate();
			candidate.setEmail("aaron@test.com");

			when(candidateDao.emailExists(candidate.getEmail())).thenReturn(false);
			when(candidateDao.save(candidate)).thenReturn(candidate);
			
			Candidate savedCandidate = candidateService.save(candidate);
			assertEquals(candidate, savedCandidate);
		}

		@Test
		public void inserCandidateWithDuplicateEmailShouldThrowValidationException() {
			assertThrows(ValidationException.class, () -> {
				final Candidate candidate = new Candidate();
				candidate.setEmail("aaron@test.com");

				when(candidateDao.emailExists(candidate.getEmail())).thenReturn(true);
				candidateService.save(candidate);
			});
		}
	}

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
	class UpdateFile {

		@Test
		public void updateFileTest() {
			final ServerFile mockedServerFile = mock(ServerFile.class);

			when(serverFileDao.save(mockedServerFile)).thenReturn(mockedServerFile);

			final ServerFile updatedServerFile = candidateService.updateFile(mockedServerFile);
			assertEquals(mockedServerFile, updatedServerFile);
		}

		@Test
		public void nullServerFileShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				candidateService.updateFile(null);
			});
		}
	}

	@BeforeEach
	public void setUp() {
		this.candidateService = new CandidateService();
		this.candidateService.setCandidateDao(this.candidateDao);
		this.candidateService.setJobCandidatureDao(this.jobCandidatureDao);
		this.candidateService.setServerFileDao(this.serverFileDao);
	}
}