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
import ged.web.view.candidate.CandidateListPanelBean;

@ExtendWith(MockitoExtension.class)
public class CandidateListPanelBeanTest {

	@Nested
	class Init {

		@Test
		public void shouldBeOk() {
			when(candidateService.findNumberOfCandidates()).thenReturn(1L);
			when(candidateService.findLastAddedCandidates(6)).thenReturn(mockCandidates());

			candidateListPanelBean.init();
			assertEquals(1, candidateListPanelBean.getNumberOfCandidates());
			assertEquals(1, candidateListPanelBean.getLastAddedCandidates().size());
		}
	}

	@Nested
	class NewCandidate {

		@Test
		public void shouldReturnCandidateEditUrl() {
			final String newCandidateUrl = candidateListPanelBean.newCandidate();
			assertEquals("/faces/candidate/candidateEdit?faces-redirect=true", newCandidateUrl);
		}
	}

	private CandidateListPanelBean candidateListPanelBean;

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
		final Candidate candidate = this.mockCandidate();
		candidates.add(candidate);
		return candidates;
	}

	@BeforeEach
	public void setUp() {
		this.candidateListPanelBean = new CandidateListPanelBean();
		this.candidateListPanelBean.setCandidateService(this.candidateService);
	}
}
