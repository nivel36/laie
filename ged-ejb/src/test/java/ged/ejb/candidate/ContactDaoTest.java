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
import ged.ejb.core.model.Page;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.core.model.search.SortField;

@ExtendWith(MockitoExtension.class)
public class ContactDaoTest {

	@Nested
	class Search {

		@Test
		public void emptyTextShouldReturnList() {
			final SearchResult<Contact> searchResult = new SearchResult<>(new ArrayList<>(), 0);
			when(persistenceFacade.search(Contact.class, Page.ALL_RESULTS, new ArrayList<SortField>(), null, "", "_name",
					"_surname", "_email")).thenReturn(searchResult);
			final List<Contact> users = contactDao.search("", Page.ALL_RESULTS).getResultData();
			assertEquals(0, users.size());
		}

		@Test
		public void nullTextShouldReturnList() {
			final SearchResult<Contact> searchResult = new SearchResult<>(new ArrayList<>(), 0);
			when(persistenceFacade.search(Contact.class, Page.ALL_RESULTS, new ArrayList<SortField>(), null, null, "_name",
					"_surname", "_email")).thenReturn(searchResult);

			final List<Contact> users = contactDao.search(null, Page.ALL_RESULTS).getResultData();
			assertEquals(0, users.size());
		}

		@Test
		public void validTextshouldReturnList() {
			final Contact contact = new Contact();
			contact.setName("Aaron");
			contact.setSurname("Smith");

			final List<Contact> contacts = new ArrayList<>();
			contacts.add(contact);

			final SearchResult<Contact> searchResult = new SearchResult<>(contacts, 1);

			when(persistenceFacade.search(Contact.class, Page.ALL_RESULTS, new ArrayList<SortField>(), null, "Aaron", "_name",
					"_surname", "_email")).thenReturn(searchResult);

			final List<Contact> returnedContacts = contactDao.search("Aaron", Page.ALL_RESULTS).getResultData();
			assertEquals("Smith", returnedContacts.get(0).getSurname());
		}
	}

	private ContactDao contactDao;

	@Mock
	private PersistenceFacade persistenceFacade;

	@BeforeEach
	public void setUp() {
		this.contactDao = new ContactDao();
		this.contactDao.setPersistenceFacade(this.persistenceFacade);
	}

}
