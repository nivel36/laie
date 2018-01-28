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
import ged.web.view.candidate.CandidateSearchBean;

@RunWith(MockitoJUnitRunner.class)
public class CandidateSearchBeanTest {

	private CandidateSearchBean candidateSearchBean;

	@Mock
	private CandidateService candidateService;

	@Test
	public void cleanTest() {
		Mockito.when(this.candidateService.search(new ArrayList<String>())).thenReturn(mockCandidates());
		this.candidateSearchBean.setSearchText("Test");
		this.candidateSearchBean.clean();
		Assert.assertEquals(null, this.candidateSearchBean.getSearchText());
		Assert.assertEquals(1, this.candidateSearchBean.getCandidates().size());
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

	@Before
	public void setUp() {
		this.candidateSearchBean = new CandidateSearchBean();
		this.candidateSearchBean.setCandidateService(this.candidateService);
	}

}
