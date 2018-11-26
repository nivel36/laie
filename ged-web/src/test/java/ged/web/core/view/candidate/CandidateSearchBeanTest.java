package ged.web.core.view.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.web.view.candidate.CandidateSearchBean;

@ExtendWith(MockitoExtension.class)
public class CandidateSearchBeanTest {

	@Nested
	class Search {
		@Test
		public void validTextshouldReturnCandidate() {
			when(candidateService.search("Aaron", Page.ALL)).thenReturn(mockCandidates());
			candidateSearchBean.setSearchText("Aaron");
			candidateSearchBean.search();
			assertEquals("Aaron", candidateSearchBean.getSearchText());
			assertEquals(1, candidateSearchBean.getCandidates().size());
		}
	}

	private CandidateSearchBean candidateSearchBean;

	@Mock
	private CandidateService candidateService;

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

	@BeforeEach
	public void setUp() {
		this.candidateSearchBean = new CandidateSearchBean();
		this.candidateSearchBean.setCandidateService(this.candidateService);
	}
}
