package ged.ejb.core.file;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class FileJpaDao extends AbstractDao<File> {

	public List<File> findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		return this.findByQuery(File.class, "ServerFile.findByCandidate", map("candidate", candidate),
				Page.ALL_RESULTS);
	}

	@Override
	protected Class<File> getType() {
		return File.class;
	}

}