package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;

public interface CandidateDao extends Dao<Long, Candidate> {

	List<FileType> findAllFileTypes();

	List<Candidate> searchByNameAndSurename(String name, String surename, Boolean showDeleted);

	Candidate findCandidateAndFiles(Long id);
}