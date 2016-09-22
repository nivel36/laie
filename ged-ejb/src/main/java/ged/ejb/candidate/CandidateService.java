package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.core.FileType;

public interface CandidateService extends AuditedService<Candidate> {

	List<FileType> findAllFileTypes();

	Candidate findCandidateAndFiles(Long id);

	List<Candidate> searchByNameAndSurename(String name, String surename);

	List<Candidate> searchByNameAndSurename(String name, String surename, Boolean showDeleted);

}