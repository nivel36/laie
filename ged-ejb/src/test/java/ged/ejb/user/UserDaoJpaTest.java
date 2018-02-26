package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

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

	@Test
	public void findAllTest() {
		when(this.persistenceFacade.findByQuery(User.class, "User.findAll", null, 0, 0))
				.thenReturn(new ArrayList<User>());
		final List<User> users = this.userDaoJpa.findAll();
		Assert.assertEquals(0, users.size());
	}

	@Test
	public void findSubordinateUsersNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findSubordinateUsers(null);
	}

	@Test
	public void findSubordinateUsersTest() {
		final User user = new User();
		user.setId(1L);
		final User subordinateUser = new User();
		subordinateUser.setId(2L);
		subordinateUser.setManager(user);
		final List<User> subordinateUsers = new ArrayList<>();
		subordinateUsers.add(subordinateUser);
		when(this.persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), 0, 0))
				.thenReturn(subordinateUsers);
		final List<User> subordinateUsersFromDataBase = this.userDaoJpa.findSubordinateUsers(user);
		Assert.assertEquals(1, subordinateUsersFromDataBase.size());
	}

	@Test
	public void findSubordinateUsersWithoutSubordinatesTest() {
		final User user = new User();
		user.setId(1L);
		when(this.persistenceFacade.findByQuery(User.class, "User.findSubordinateUsers", map("id", 1L), 0, 0))
				.thenThrow(new NoResultException());
		final List<User> subordinateUsersFromDataBases = this.userDaoJpa.findSubordinateUsers(user);
		Assert.assertEquals(0, subordinateUsersFromDataBases.size());
	}

	@Test
	public void findUserByEmailNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		this.userDaoJpa.findUserByEmail(null);
	}

	@Test
	public void findUserByEmailTest() {
		final User user = new User();
		user.setEmail("aaron@test.com");
		when(this.persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "aaron@test.com")))
				.thenReturn(user);
		final User userInDataBase = this.userDaoJpa.findUserByEmail("aaron@test.com");
		Assert.assertEquals(user, userInDataBase);
	}

	@Test
	public void findUserByNonExistingEmailTest() {
		when(this.persistenceFacade.findByQuery(User.class, "User.findByEmail", map("email", "aaron@test.com")))
				.thenThrow(new NoResultException());
		final User userInDataBase = this.userDaoJpa.findUserByEmail("aaron@test.com");
		Assert.assertNull(userInDataBase);
	}

	@Before
	public void setUp() {
		this.userDaoJpa = new UserDaoJpa();
		this.userDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}
}
