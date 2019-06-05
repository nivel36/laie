package ged.ejb.person;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class PersonDao extends AbstractDao<Person> {

	@Override
	protected Class<Person> getType() {
		return Person.class;
	}
	
	@Override
	public String[] searchFields() {
		return new String[] { "_name", "_surname" };
	}
}
