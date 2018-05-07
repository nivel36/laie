package ged.ejb.core.file;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Dao;

public interface ServerFileDao extends Dao<ServerFile> {

	List<ServerFile> findByCandidate(Candidate candidate);

}
