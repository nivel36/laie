package ged.ejb;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface ServerFileDao extends Dao<ServerFile> {

	List<ServerFile> findByCandidateId(long candidateId);

}
