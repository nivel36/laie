package ged.ejb.core.tag;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface TagDao extends Dao<Tag> {

	List<Tag> searchByLabel(final String label);
}