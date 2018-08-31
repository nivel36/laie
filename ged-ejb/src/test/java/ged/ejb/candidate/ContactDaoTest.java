package ged.ejb.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactDao;
import ged.ejb.core.model.PersistenceFacade;

@ExtendWith(MockitoExtension.class)
public class ContactDaoTest {

	private ContactDao contactDao;

	@Mock
	private PersistenceFacade persistenceFacade;

	@Nested
	class Search {

		@Test
		public void emptyTextShouldReturnList() {
			when(persistenceFacade.search(Contact.class, "", "name", "surname", "email")).thenReturn(new ArrayList<>());
			final List<Contact> users = contactDao.search("");
			assertEquals(0, users.size());
		}

		@Test
		public void nullTextShouldReturnList() {
			when(persistenceFacade.search(Contact.class, null, "name", "surname", "email"))
					.thenReturn(new ArrayList<>());

			final List<Contact> users = contactDao.search(null);
			assertEquals(0, users.size());
		}

		@Test
		public void validTextshouldReturnList() {
			final Contact contact = new Contact();
			contact.setName("Aaron");
			contact.setSurname("Smith");

			final List<Contact> contacts = new ArrayList<>();
			contacts.add(contact);

			when(persistenceFacade.search(Contact.class, "Aaron", "name", "surname", "email")).thenReturn(contacts);

			final List<Contact> returnedContacts = contactDao.search("Aaron");
			assertEquals("Smith", returnedContacts.get(0).getSurname());
		}
	}

	@BeforeEach
	public void setUp() {
		this.contactDao = new ContactDao();
		this.contactDao.setPersistenceFacade(this.persistenceFacade);
	}

}
