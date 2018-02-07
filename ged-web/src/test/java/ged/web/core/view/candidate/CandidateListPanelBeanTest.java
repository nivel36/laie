package ged.web.core.view.candidate;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.view.candidate.CandidateListPanelBean;

@RunWith(MockitoJUnitRunner.class)
public class CandidateListPanelBeanTest {

	private CandidateListPanelBean candidateListPanelBean;

	@Mock
	private CandidateService candidateService;

	@Test
	public void initTest() {
		Mockito.when(this.candidateService.findNumberOfCandidates()).thenReturn(1L);
		Mockito.when(this.candidateService.findLastAddedCandidates(6)).thenReturn(mockCandidates());
		this.candidateListPanelBean.init();
		Assert.assertEquals(1, this.candidateListPanelBean.getNumberOfCandidates());
		Assert.assertEquals(1, this.candidateListPanelBean.getLastAddedCandidates().size());
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

	@Test
	public void newCanidateTest() {
		final String newCandidateUrl = this.candidateListPanelBean.newCandidate();
		Assert.assertEquals("/faces/candidate/candidate?faces-redirect=true", newCandidateUrl);
	}

	@Before
	public void setUp() {
		this.candidateListPanelBean = new CandidateListPanelBean();
		this.candidateListPanelBean.setCandidateService(this.candidateService);
	}
}
