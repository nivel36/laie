package ged.web.view.candidate;

import java.util.List;

import ged.ejb.candidate.Candidate;

public interface CandidateSelecteable {

	void onCandidatesSelect(List<Candidate> selectedCandidates);
}