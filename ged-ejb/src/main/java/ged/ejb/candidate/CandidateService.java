package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.AuditedService;
import ged.ejb.core.FileType;
import ged.ejb.curriculum.Curriculum;

@Local
public interface CandidateService extends AuditedService<Candidate> {

	public List<FileType> findAllFileTypes();

	public List<Candidate> findByNameAndSurename(String name, String surename);

	public List<Candidate> findByNameAndSurename(String name, String surename, boolean showDeleted);

	public Curriculum findCurriculumByCandidateId(final long id);

}
