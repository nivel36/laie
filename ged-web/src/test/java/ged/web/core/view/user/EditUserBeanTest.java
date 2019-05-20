package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.Flash;
import javax.faces.validator.ValidatorException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import ged.web.view.user.EditUserBean;

@ExtendWith(MockitoExtension.class)
public class EditUserBeanTest {

	@Nested
	class GetUser {

		@Test
		public void getUserTest() {
			final User user = EditUserBeanTest.this.mockUser();
			EditUserBeanTest.this.userEditBean.setUser(user);
			final User returnedUser = EditUserBeanTest.this.userEditBean.getUser();
			assertEquals(user, returnedUser);
		}
	}

	@Nested
	class Init {

		@Test
		public void updateUserShouldHavelEmail() {
			final User mockUser = EditUserBeanTest.this.mockUser();
			when(EditUserBeanTest.this.flash.containsKey("user")).thenReturn(true);
			when(EditUserBeanTest.this.flash.get("user")).thenReturn(mockUser);
			when(EditUserBeanTest.this.sessionUser.hasPermissionToEdit(mockUser)).thenReturn(true);
			EditUserBeanTest.this.userEditBean.init();

			assertEquals("abel@test.com", EditUserBeanTest.this.userEditBean.getUser().getEmail());
		}
	}

	@Nested
	class Save {

		@Test
		public void userShouldBeOk() {
			final User user = EditUserBeanTest.this.mockUser();
			EditUserBeanTest.this.userEditBean.setUser(user);

			final User updatedUser = EditUserBeanTest.this.mockUser();

			when(EditUserBeanTest.this.sessionUser.hasPermissionToEdit(user)).thenReturn(true);
			when(EditUserBeanTest.this.userService.save(user)).thenReturn(updatedUser);

			EditUserBeanTest.this.userEditBean.save();

			assertEquals("abel@test.com", updatedUser.getEmail());
			assertEquals(1L, updatedUser.getId());
		}
	}

	@Nested
	class SearchManager {

		@Test
		public void nullSearchShouldReturnEmptyList() {
			final List<User> managers = EditUserBeanTest.this.userEditBean.searchManager(null);
			assertEquals(0, managers.size());
		}

		@Test
		public void shortTextSearchShouldReturnEmptyList() {
			final List<User> managers = EditUserBeanTest.this.userEditBean.searchManager("as");
			assertEquals(0, managers.size());
		}

		@Test
		public void validSearchShouldReturnUserList() {
			final List<User> mockedManagers = EditUserBeanTest.this.mockListOfUsers();
			SearchResult<User> searchResult = new SearchResult<>(mockedManagers,mockedManagers.size());
			when(EditUserBeanTest.this.userService.search("Abe", Page.ALL)).thenReturn(searchResult);

			final List<User> managers = EditUserBeanTest.this.userEditBean.searchManager("Abe");

			assertEquals("abel@test.com", managers.get(0).getEmail());
		}
	}

	@Nested
	class UploadImage {

		@Test
		public void emptyFileShouldNotSetImageNameToUser() throws IOException {
			final User user = EditUserBeanTest.this.mockUser();
			user.setEmail("abel@test.com");
			EditUserBeanTest.this.userEditBean.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			EditUserBeanTest.this.userEditBean.uploadImage(event);
			assertNull(user.getImageFileName());
		}

		@Test
		public void nullEventShouldThrowNullPointerException() throws IOException {
			assertThrows(NullPointerException.class, () -> {
				EditUserBeanTest.this.userEditBean.uploadImage(null);
			});
		}

		@Test
		public void uploadImageShouldSetImageNameToUserObject() throws IOException {
			final User user = EditUserBeanTest.this.mockUser();
			EditUserBeanTest.this.userEditBean.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			final UploadedFile file = mock(UploadedFile.class);
			when(event.getFile()).thenReturn(file);
			final InputStream is = mock(InputStream.class);
			when(event.getFile().getInputstream()).thenReturn(is);
			when(EditUserBeanTest.this.fileUploadService.uploadImage(is)).thenReturn("uuidImageName");

			EditUserBeanTest.this.userEditBean.uploadImage(event);
			assertEquals("uuidImageName", user.getImageFileName());
		}
	}

	@Nested
	class ValidateEmail {

		@Test
		public void changeEmailToDuplicatedEmailShouldThrowValidatorException() {
			assertThrows(ValidatorException.class, () -> {
				EditUserBeanTest.this.userEditBean.setUser(EditUserBeanTest.this.mockUser());

				when(EditUserBeanTest.this.userService.isEmailInUse("bernard@test.com")).thenReturn(true);
				when(EditUserBeanTest.this.translator.message("user.error.email_exists")).thenReturn("Error message");

				EditUserBeanTest.this.userEditBean.validateEmail(null, null, "bernard@test.com");
			});
		}

		@Test
		public void changeEmailToNonDuplicatedEmailShoulbBeOk() {
			EditUserBeanTest.this.userEditBean.setUser(EditUserBeanTest.this.mockUser());
			when(EditUserBeanTest.this.userService.isEmailInUse("another.email@test.com")).thenReturn(false);
			EditUserBeanTest.this.userEditBean.validateEmail(null, null, "another.email@test.com");
		}

		@Test
		public void nullEmailShouldBeOk() {
			EditUserBeanTest.this.userEditBean.validateEmail(null, null, null);
		}

		@Test
		public void unmodifiedEmailShouldBeOk() {
			EditUserBeanTest.this.userEditBean.setUser(EditUserBeanTest.this.mockUser());
			EditUserBeanTest.this.userEditBean.validateEmail(null, null, "abel@test.com");
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

	private EditUserBean userEditBean;

	@Mock
	private UserService userService;

	private List<User> mockListOfUsers() {
		final List<User> users = new ArrayList<>();
		users.add(this.mockUser());
		return users;
	}

	private User mockUser() {
		final User user = new User();
		user.setEmail("abel@test.com");
		user.setId(1L);
		return user;
	}

	@BeforeEach
	public void setUp() {
		this.userEditBean = new EditUserBean();
		this.userEditBean.setFileUploadService(this.fileUploadService);
		this.userEditBean.setUserService(this.userService);
		this.userEditBean.setFlash(this.flash);
		this.userEditBean.setTranslator(this.translator);
		this.userEditBean.setSessionUser(this.sessionUser);
	}
}
