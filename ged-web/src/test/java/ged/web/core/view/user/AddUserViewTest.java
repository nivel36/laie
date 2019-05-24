package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.Flash;
import javax.faces.validator.ValidatorException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.core.FileUploadService;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.SearchResult;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.Translator;
import ged.web.core.view.SessionUser;
import ged.web.view.user.AddUserView;

@ExtendWith(MockitoExtension.class)
public class AddUserViewTest {

	@Nested
	class GetUser {

		@Test
		public void getUserTest() {
			final User user = mockUser();
			addUserView.setUser(user);
			final User returnedUser = addUserView.getUser();
			assertEquals(user, returnedUser);
		}
	}

	@Nested
	class Init {

		@Test
		public void newUserShouldHaveDefaultValuesNonNull() {
			when(sessionUser.isAdmin()).thenReturn(true);
			addUserView.init();
			final User user = addUserView.getUser();

			assertNull(user.getEmail());
			assertEquals(LocalDate.now(), user.getDateOfJoin());
			assertEquals("ES", user.getLanguage());
			assertEquals(Integer.valueOf(25), user.getRowsPerPage());
			assertNull(user.getLastConnection());
		}
	}

	@Nested
	class Save {

		@Test
		public void newUserShouldBeOk() {
			final User user = mockNewUser();
			addUserView.setUser(user);

			final User savedUser = mockUser();
			when(userService.save(user)).thenReturn(savedUser);

			addUserView.save();
			assertEquals("abel@test.com", addUserView.getUser().getEmail());
			assertEquals(1L, addUserView.getUser().getId());
		}
	}

	@Nested
	class SearchManager {

		@Test
		public void nullSearchShouldReturnEmptyList() {
			final List<User> managers = addUserView.searchManager(null);
			assertEquals(0, managers.size());
		}

		@Test
		public void shortTextSearchShouldReturnEmptyList() {
			final List<User> managers = addUserView.searchManager("as");
			assertEquals(0, managers.size());
		}

		@Test
		public void validSearchShouldReturnUserList() {
			final List<User> mockedManagers = mockListOfUsers();
			SearchResult<User> searchResult = new SearchResult<>(mockedManagers, mockedManagers.size());
			Mockito.when(userService.search("Abe", Page.ALL)).thenReturn(searchResult);

			final List<User> managers = addUserView.searchManager("Abe");

			assertEquals("abel@test.com", managers.get(0).getEmail());
		}
	}

	@Nested
	class UploadImage {

		@Test
		public void emptyFileShouldNotSetImageNameToUser() throws IOException {
			final User user = mockUser();
			user.setEmail("abel@test.com");
			addUserView.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			addUserView.uploadImage(event);
			assertNull(user.getImageFileName());
		}

		@Test
		public void nullEventShouldThrowNullPointerException() throws IOException {
			assertThrows(NullPointerException.class, () -> {
				addUserView.uploadImage(null);
			});
		}

		@Test
		public void uploadImageShouldSetImageNameToUserObject() throws IOException {
			final User user = mockUser();
			addUserView.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			final UploadedFile file = mock(UploadedFile.class);
			when(event.getFile()).thenReturn(file);
			final InputStream is = mock(InputStream.class);
			when(event.getFile().getInputstream()).thenReturn(is);
			when(fileUploadService.uploadImage(is)).thenReturn("uuidImageName");

			addUserView.uploadImage(event);
			assertEquals("uuidImageName", user.getImageFileName());
		}
	}

	@Nested
	class ValidateEmail {

		@Test
		public void changeEmailToDuplicatedEmailShouldThrowValidatorException() {
			assertThrows(ValidatorException.class, () -> {
				addUserView.setUser(mockUser());

				when(userService.isEmailInUse("bernard@test.com")).thenReturn(true);
				when(translator.message("user.error.email_exists")).thenReturn("Error message");

				addUserView.validateEmail(null, null, "bernard@test.com");
			});
		}

		@Test
		public void changeEmailToNonDuplicatedEmailShoulbBeOk() {
			addUserView.setUser(mockUser());
			when(userService.isEmailInUse("another.email@test.com")).thenReturn(false);
			addUserView.validateEmail(null, null, "another.email@test.com");
		}

		@Test
		public void nullEmailShouldBeOk() {
			addUserView.validateEmail(null, null, null);
		}

		@Test
		public void unmodifiedEmailShouldBeOk() {
			addUserView.setUser(mockUser());
			addUserView.validateEmail(null, null, "abel@test.com");
		}
	}

	@Mock
	private FileUploadService fileUploadService;

	@Mock
	private Flash flash;

	@Mock
	private SessionUser sessionUser;

	@Mock
	private Translator translator;

	private AddUserView addUserView;

	@Mock
	private UserService userService;

	private List<User> mockListOfUsers() {
		final List<User> users = new ArrayList<>();
		users.add(mockUser());
		return users;
	}

	private User mockNewUser() {
		final User user = new User();
		user.setEmail("abel@test.com");
		return user;
	}

	private User mockUser() {
		final User user = new User();
		user.setEmail("abel@test.com");
		user.setId(1L);
		return user;
	}

	@BeforeEach
	public void setUp() {
		this.addUserView = new AddUserView();
		this.addUserView.setFileUploadService(this.fileUploadService);
		this.addUserView.setUserService(this.userService);
		this.addUserView.setFlash(this.flash);
		this.addUserView.setTranslator(this.translator);
		addUserView.setSessionUser(sessionUser);
	}

}
