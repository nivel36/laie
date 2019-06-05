package ged.ejb.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class PersonService extends AbstractService<Person> {

	@Inject
	@Repository
	private PersonDao personDao;

	@Override
	protected AbstractDao<Person> getDao() {
		return personDao;
	}
	
	
}
