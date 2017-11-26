package ged.ejb.core.tag;

import java.util.List;

import ged.ejb.core.Service;

public interface TagService extends Service<Tag> {

	List<Tag> searchByLabel(final String label);
}