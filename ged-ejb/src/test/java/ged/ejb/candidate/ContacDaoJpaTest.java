package ged.ejb.candidate;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactDaoJpa;
import ged.ejb.core.model.PersistenceFacade;

@RunWith(MockitoJUnitRunner.class)
public class ContacDaoJpaTest {

	private ContactDaoJpa contactDaoJpa;

	@Mock
	private PersistenceFacade persistenceFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void searchNullTest() {
		this.thrown.expect(NullPointerException.class);
		this.contactDaoJpa.search(null);
	}

	@Test
	public void searchTest() {
		final Contact contact = new Contact();
		contact.setName("Aaron");
		contact.setSurname("Smith");
		final List<Contact> contacts = new ArrayList<>();
		contacts.add(contact);
		when(this.persistenceFacade.search(Contact.class, "Aaron", "name", "email")).thenReturn(contacts);
		final List<Contact> returnedContacts = this.contactDaoJpa.search("Aaron");
		Assert.assertEquals(1, returnedContacts.size());
		Assert.assertEquals("Smith", returnedContacts.get(0).getSurname());
	}

	@Before
	public void setUp() {
		this.contactDaoJpa = new ContactDaoJpa();
		this.contactDaoJpa.setPersistenceFacade(this.persistenceFacade);
	}

}
