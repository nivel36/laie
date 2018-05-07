package ged.ejb.candidate;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.ServerFile;
import ged.ejb.ServerFileDao;
import ged.ejb.job.offer.JobCandidatureDao;
import ged.ejb.job.offer.JobOffer;

@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceImplTest {

	@Mock
	private CandidateDao candidateDao;

	private CandidateServiceImpl candidateServiceImpl;

	@Mock
	private JobCandidatureDao jobCandidatureDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private ServerFileDao uploadedServerFileDao;

	@Test
	public void findAllByJobOfferTest() {
		final JobOffer jobOffer = new JobOffer();
		Mockito.when(this.candidateDao.findCandidatesByJobOffer(jobOffer)).thenReturn(new ArrayList<>());
		final List<Candidate> candidatesFromRepository = this.candidateServiceImpl.findCandidatesByJobOffer(jobOffer);
		Assert.assertEquals(0, candidatesFromRepository.size());
	}

	@Test
	public void findAllByNullJobOfferTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateServiceImpl.findCandidatesByJobOffer(null);
	}

	@Test
	public void findCandidateAndFilesByWrongIdTest() {
		this.thrown.expect(IllegalArgumentException.class);
		this.candidateServiceImpl.findAllCandidateDataById(0);
	}

	@Test
	public void findCandidateAndFilesTest() {
		final Candidate mockedCandidate = Mockito.mock(Candidate.class);
		Mockito.when(this.candidateDao.findAllCandidateDataById(1L)).thenReturn(mockedCandidate);
		final Candidate candidateFromRepository = this.candidateServiceImpl.findAllCandidateDataById(1L);
		Assert.assertNotNull(candidateFromRepository);
	}

	@Test
	public void findFileByWrongIdTest() {
		this.thrown.expect(IllegalArgumentException.class);
		this.candidateServiceImpl.findFile(0);
	}

	@Test
	public void findFileTest() {
		final ServerFile mockedUploadedServerFile = Mockito.mock(ServerFile.class);
		Mockito.when(this.uploadedServerFileDao.find(1L)).thenReturn(mockedUploadedServerFile);
		final ServerFile uploadedServerFileFromRepository = this.candidateServiceImpl.findFile(1L);
		Assert.assertEquals(mockedUploadedServerFile, uploadedServerFileFromRepository);
	}

	@Test
	public void findLastAddedCandidatesByWrongNumberTest() {
		this.thrown.expect(IllegalArgumentException.class);
		this.candidateServiceImpl.findLastAddedCandidates(-1);
	}

	@Test
	public void findLastAddedCandidatesTest() {
		Mockito.when(this.candidateDao.findLastAddedCandidates(1)).thenReturn(new ArrayList<>());
		final List<Candidate> candidatesFromRepository = this.candidateServiceImpl.findLastAddedCandidates(1);
		Assert.assertEquals(0, candidatesFromRepository.size());
	}

	@Test
	public void findNumberOfCandidatesTest() {
		Mockito.when(this.candidateDao.findNumberOfCandidates()).thenReturn(1L);
		final long numberOfCandidates = this.candidateServiceImpl.findNumberOfCandidates();
		Assert.assertEquals(1, numberOfCandidates);
	}

	@Test
	public void insertDuplicatedTest() {
		final Candidate candidate = new Candidate();
		candidate.setEmail("aaron@test.com");
		Mockito.when(this.candidateDao.emailExists(candidate.getEmail())).thenReturn(true);
		this.thrown.expect(ValidationException.class);
		this.candidateServiceImpl.insert(candidate);
	}

	@Test
	public void insertFileNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateServiceImpl.addFile(null);
	}

	@Test
	public void insertFileTest() {
		final ServerFile mockedUploadedServerFile = Mockito.mock(ServerFile.class);
		this.candidateServiceImpl.addFile(mockedUploadedServerFile);
	}

	@Test
	public void insertNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateServiceImpl.insert(null);
	}

	@Test
	public void insertTest() {
		final Candidate candidate = new Candidate();
		candidate.setEmail("aaron@test.com");
		Mockito.when(this.candidateDao.emailExists(candidate.getEmail())).thenReturn(false);
		this.candidateServiceImpl.insert(candidate);
	}

	@Before
	public void setUp() {
		this.candidateServiceImpl = new CandidateServiceImpl(this.candidateDao, this.uploadedServerFileDao, this.jobCandidatureDao);
	}

	@Test
	public void updateDuplicatedEmailCandidateTest() {
		final Candidate candidate = new Candidate();
		candidate.setEmail("aaron@test.com");
		candidate.setId(1L);
		Mockito.when(this.candidateDao.emailExists(candidate.getEmail())).thenReturn(true);
		Mockito.when(this.candidateDao.find(candidate.getId())).thenReturn(Mockito.mock(Candidate.class));
		Mockito.when(this.candidateDao.update(candidate)).thenReturn(candidate);
		this.thrown.expect(ValidationException.class);
		this.candidateServiceImpl.update(candidate);
	}

	@Test
	public void updateFileTest() {
		final ServerFile mockedUploadedServerFile = Mockito.mock(ServerFile.class);
		Mockito.when(this.uploadedServerFileDao.update(mockedUploadedServerFile)).thenReturn(mockedUploadedServerFile);
		final ServerFile uploadedServerFileFromRepository = this.candidateServiceImpl.updateFile(mockedUploadedServerFile);
		Assert.assertEquals(mockedUploadedServerFile, uploadedServerFileFromRepository);
	}

	@Test
	public void updateNullFileTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateServiceImpl.updateFile(null);
	}

	@Test
	public void updateNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.candidateServiceImpl.update(null);
	}

	@Test
	public void updateTest() {
		final Candidate candidate = new Candidate();
		candidate.setEmail("aaron@test.com");
		candidate.setId(1L);
		Mockito.when(this.candidateDao.emailExists(candidate.getEmail())).thenReturn(false);
		Mockito.when(this.candidateDao.find(candidate.getId())).thenReturn(candidate);
		Mockito.when(this.candidateDao.update(candidate)).thenReturn(candidate);
		final Candidate candidateFromRepository = this.candidateServiceImpl.update(candidate);
		Assert.assertEquals(candidate, candidateFromRepository);
	}
}