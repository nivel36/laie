package ged.ejb.user;

import static ged.ejb.core.model.QueryParameter.with;

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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoJpaTest {

	private UserDaoJpa userDaoJpa;

	@Mock
	private PersistenceFacade persistenceFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void emailExistNullValueTest() {
		this.thrown.expect(NullPointerException.class);
		userDaoJpa.emailExists(null);
	}

	@Before
	public void setUp() {
		this.userDaoJpa = new UserDaoJpa();
		this.userDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}
}
