package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.AuditedService;
import ged.ejb.core.FileType;
import ged.ejb.curriculum.Curriculum;

@Local
public interface CandidateService extends AuditedService<Candidate> {

	List<FileType> findAllFileTypes();

	List<Candidate> findByNameAndSurename(String name, String surename);

	List<Candidate> findByNameAndSurename(String name, String surename, boolean showDeleted);

	Candidate findCandidateAndFiles(Long id);

	Curriculum findCurriculumByCandidateId(final Long id);
}