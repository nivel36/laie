package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.curriculum.Curriculum;

public interface CandidateDao extends Dao<Long, Candidate> {

	public List<FileType> findAllFileTypes();

	public List<Candidate> findByNameAndSurename(String name, String surename, boolean showDeleted);

	public Curriculum findCurriculumByCandidateId(final long id);
}