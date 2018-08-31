package ged.web.core.view.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.view.candidate.CandidateSearchBean;

@ExtendWith(MockitoExtension.class)
public class CandidateSearchBeanTest {

	private CandidateSearchBean candidateSearchBean;

	@Mock
	private CandidateService candidateService;

	@Test
	public void cleanTest() {
		when(this.candidateService.search(null)).thenReturn(mockCandidates());
		this.candidateSearchBean.setSearchText("Aaron");
		this.candidateSearchBean.clean();
		assertEquals(null, this.candidateSearchBean.getSearchText());
		assertEquals(1, this.candidateSearchBean.getCandidates().size());
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
	public void searchTest() {
		when(this.candidateService.search("Aaron")).thenReturn(mockCandidates());
		this.candidateSearchBean.setSearchText("Aaron");
		this.candidateSearchBean.search();
		assertEquals("Aaron", this.candidateSearchBean.getSearchText());
		assertEquals(1, this.candidateSearchBean.getCandidates().size());
	}

	@BeforeEach
	public void setUp() {
		this.candidateSearchBean = new CandidateSearchBean();
		this.candidateSearchBean.setCandidateService(this.candidateService);
	}
}
