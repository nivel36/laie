package ged.ejb.user;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.core.model.PersistenceFacade;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoJpaTest {

	@Mock
	private PersistenceFacade persistenceFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private UserDaoJpa userDaoJpa;

	@Test
	public void emailExistNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.emailExists(null);
	}

	@Before
	public void setUp() {
		this.userDaoJpa = new UserDaoJpa();
		this.userDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}
}
