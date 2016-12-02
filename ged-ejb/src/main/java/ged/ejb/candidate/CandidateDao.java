package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;

@Local
public interface CandidateDao extends Dao<Long, Candidate> {

	List<FileType> findAllFileTypes();

	Candidate findCandidateAndFiles(Long id);

	List<Candidate> searchByNameAndSurename(String name, String surename, String position, Boolean showDeleted);
}