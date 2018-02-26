package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;
import static org.mockito.Mockito.when;

import org.junit.Assert;
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
		this.userDaoJpa.emailExist(null);
	}

	@Test
	public void emailExistTest() {
		when(this.persistenceFacade.findByQuery(Boolean.class, "User.emailExists", map("email", "aaron@test.com")))
				.thenReturn(Boolean.TRUE);
		final boolean result = this.userDaoJpa.emailExist("aaron@test.com");
		Assert.assertTrue(result);
	}

	@Test
	public void existMoreThanOneAdminTest() {
		when(this.persistenceFacade.findByQuery(Boolean.class, "User.existsMoreThanOneAdmin", null))
				.thenReturn(Boolean.TRUE);
		final boolean result = this.userDaoJpa.existMoreThanOneAdmin();
		Assert.assertTrue(result);
	}

	@Before
	public void setUp() {
		this.userDaoJpa = new UserDaoJpa();
		this.userDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}
}
