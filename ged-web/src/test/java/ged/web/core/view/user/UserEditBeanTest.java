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
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.Translator;
import ged.web.core.view.SessionUser;
import ged.web.view.user.UserEditBean;

@ExtendWith(MockitoExtension.class)
public class UserEditBeanTest {

	@Nested
	class Cancel {

		@Test
		public void cancelUserShouldReturnUserUrl() {
			final User user = mockUser();
			when(flash.containsKey("user")).thenReturn(true);
			when(flash.get("user")).thenReturn(user);
			when(sessionUser.hasPermissionToEdit(user)).thenReturn(true);
			userEditBean.init();

			final String url = userEditBean.cancel();
			assertEquals("/faces/user/user?faces-redirect=true&userId=1", url);
		}

		@Test
		public void newUserShouldReturnUserSearchUrl() {
			when(sessionUser.isAdmin()).thenReturn(true);
			userEditBean.init();
			final String url = userEditBean.cancel();
			assertEquals("/faces/user/userSearch", url);
		}
	}

	@Nested
	class GetUser {

		@Test
		public void getUserTest() {
			final User user = mockUser();
			userEditBean.setUser(user);
			final User returnedUser = userEditBean.getUser();
			assertEquals(user, returnedUser);
		}
	}

	@Nested
	class Init {

		@Test
		public void newUserShouldHaveDefaultValuesNonNull() {
			when(sessionUser.isAdmin()).thenReturn(true);
			userEditBean.init();
			final User user = userEditBean.getUser();

			assertNull(user.getEmail());
			assertEquals(LocalDate.now(), user.getDateOfJoin());
			assertEquals("ES", user.getLanguage());
			assertEquals(Integer.valueOf(25), user.getRowsPerPage());
			assertNull(user.getLastConnection());
		}

		@Test
		public void updateUserShouldHavelEmail() {
			final User mockUser = mockUser();
			when(flash.containsKey("user")).thenReturn(true);
			when(flash.get("user")).thenReturn(mockUser);
			when(sessionUser.hasPermissionToEdit(mockUser)).thenReturn(true);
			userEditBean.init();

			assertEquals("abel@test.com", userEditBean.getUser().getEmail());
		}
	}

	@Nested
	class Save {

		@Test
		public void newUserShouldBeOk() {
			final User user = mockNewUser();
			userEditBean.setUser(user);

			final User savedUser = mockUser();
			when(sessionUser.hasPermissionToEdit(user)).thenReturn(true);
			when(userService.save(user)).thenReturn(savedUser);

			userEditBean.save();
			assertEquals("abel@test.com", userEditBean.getUser().getEmail());
			assertEquals(1L, userEditBean.getUser().getId());
		}

		@Test
		public void updateUserShouldBeOk() {
			final User user = mockUser();
			userEditBean.setUser(user);

			final User updatedUser = mockUser();

			when(sessionUser.hasPermissionToEdit(user)).thenReturn(true);
			when(userService.save(user)).thenReturn(updatedUser);

			userEditBean.save();

			assertEquals("abel@test.com", updatedUser.getEmail());
			assertEquals(1L, updatedUser.getId());
		}
	}

	@Nested
	class SearchManager {

		@Test
		public void nullSearchShouldReturnEmptyList() {
			final List<User> managers = userEditBean.searchManager(null);
			assertEquals(0, managers.size());
		}

		@Test
		public void shortTextSearchShouldReturnEmptyList() {
			final List<User> managers = userEditBean.searchManager("as");
			assertEquals(0, managers.size());
		}

		@Test
		public void validSearchShouldReturnUserList() {
			final List<User> mockedManagers = mockListOfUsers();
			Mockito.when(userService.search("Abe", Page.ALL)).thenReturn(mockedManagers);

			final List<User> managers = userEditBean.searchManager("Abe");

			assertEquals("abel@test.com", managers.get(0).getEmail());
		}
	}

	@Nested
	class UploadImage {

		@Test
		public void emptyFileShouldNotSetImageNameToUser() throws IOException {
			final User user = mockUser();
			user.setEmail("abel@test.com");
			userEditBean.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			userEditBean.uploadImage(event);
			assertNull(user.getImageFileName());
		}

		@Test
		public void nullEventShouldThrowNullPointerException() throws IOException {
			assertThrows(NullPointerException.class, () -> {
				userEditBean.uploadImage(null);
			});
		}

		@Test
		public void uploadImageShouldSetImageNameToUserObject() throws IOException {
			final User user = mockUser();
			userEditBean.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			final UploadedFile file = mock(UploadedFile.class);
			when(event.getFile()).thenReturn(file);
			final InputStream is = mock(InputStream.class);
			when(event.getFile().getInputstream()).thenReturn(is);
			when(fileUploadService.uploadImage(is)).thenReturn("uuidImageName");

			userEditBean.uploadImage(event);
			assertEquals("uuidImageName", user.getImageFileName());
		}
	}

	@Nested
	class ValidateEmail {

		@Test
		public void changeEmailToDuplicatedEmailShouldThrowValidatorException() {
			assertThrows(ValidatorException.class, () -> {
				userEditBean.setUser(mockUser());

				when(userService.isEmailInUse("bernard@test.com")).thenReturn(true);
				when(translator.message("user.error.email_exists")).thenReturn("Error message");

				userEditBean.validateEmail(null, null, "bernard@test.com");
			});
		}

		@Test
		public void changeEmailToNonDuplicatedEmailShoulbBeOk() {
			userEditBean.setUser(mockUser());
			when(userService.isEmailInUse("another.email@test.com")).thenReturn(false);
			userEditBean.validateEmail(null, null, "another.email@test.com");
		}

		@Test
		public void nullEmailShouldBeOk() {
			userEditBean.validateEmail(null, null, null);
		}

		@Test
		public void unmodifiedEmailShouldBeOk() {
			userEditBean.setUser(mockUser());
			userEditBean.validateEmail(null, null, "abel@test.com");
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

	private UserEditBean userEditBean;

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
		this.userEditBean = new UserEditBean();
		this.userEditBean.setFileUploadService(this.fileUploadService);
		this.userEditBean.setUserService(this.userService);
		this.userEditBean.setFlash(this.flash);
		this.userEditBean.setTranslator(this.translator);
		userEditBean.setSessionUser(sessionUser);
	}
}
