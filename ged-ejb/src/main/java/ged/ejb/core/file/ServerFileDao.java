package ged.ejb.core.file;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class ServerFileDao extends AbstractDao<ServerFile> {

	public List<ServerFile> findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		return this.findByQuery(ServerFile.class, "ServerFile.findByCandidate", map("candidate", candidate), Page.ALL);
	}

	@Override
	protected Class<ServerFile> getType() {
		return ServerFile.class;
	}

	@Override
	public List<ServerFile> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}