package ged.ejb.core.file;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class ServerFileDaoJpa extends AbstractDaoJpa<ServerFile> implements ServerFileDao {

	@Override
	public List<ServerFile> findByCandidateId(final long candidateId) {
		return this.findByQuery(ServerFile.class, "ServerFile.findByCandidateId", map("candidateId", candidateId), 0, 0);
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