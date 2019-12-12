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
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.Translator;
import ged.web.core.view.SessionUser;
import ged.web.view.user.EditUserView;

@ExtendWith(MockitoExtension.class)
public class EditUserViewTest {

	@Nested
	class GetUser {

		@Test
		public void getUserTest() {
			final User user = EditUserViewTest.this.mockUser();
			EditUserViewTest.this.userEditView.setUser(user);
			final User returnedUser = EditUserViewTest.this.userEditView.getUser();
			assertEquals(user, returnedUser);
		}
	}

	@Nested
	class Init {

		@Test
		public void updateUserShouldHavelEmail() {
			final User mockUser = EditUserViewTest.this.mockUser();
			when(EditUserViewTest.this.flash.containsKey("user")).thenReturn(true);
			when(EditUserViewTest.this.flash.get("user")).thenReturn(mockUser);
			when(EditUserViewTest.this.sessionUser.isAdmin()).thenReturn(true);
			EditUserViewTest.this.userEditView.init();

			assertEquals("abel@test.com", EditUserViewTest.this.userEditView.getUser().getEmail());
		}
	}

	@Nested
	class Save {

		@Test
		public void userShouldBeOk() {
			final User user = EditUserViewTest.this.mockUser();
			EditUserViewTest.this.userEditView.setUser(user);

			final User updatedUser = EditUserViewTest.this.mockUser();

			when(EditUserViewTest.this.sessionUser.isAdmin()).thenReturn(true);
			when(EditUserViewTest.this.userService.save(user)).thenReturn(updatedUser);

			EditUserViewTest.this.userEditView.save();

			assertEquals("abel@test.com", updatedUser.getEmail());
			assertEquals(1L, updatedUser.getId());
		}
	}

	@Nested
	class SearchManager {

		@Test
		public void validSearchShouldReturnUserList() {
			final List<User> mockedManagers = EditUserViewTest.this.mockListOfUsers();
			final SearchResult<User> searchResult = new SearchResult<>(mockedManagers, mockedManagers.size());
			when(EditUserViewTest.this.userService.search("Abe", Page.TEN_RESULTS_PER_PAGE)).thenReturn(searchResult);

			final List<User> managers = EditUserViewTest.this.userEditView.queryManager("Abe");

			assertEquals("abel@test.com", managers.get(0).getEmail());
		}
	}

	@Nested
	class UploadImage {

		@Test
		public void emptyFileShouldNotSetImageNameToUser() throws IOException {
			final User user = EditUserViewTest.this.mockUser();
			user.setEmail("abel@test.com");
			EditUserViewTest.this.userEditView.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			EditUserViewTest.this.userEditView.uploadImage(event);
			assertNull(user.getImageFileName());
		}

		@Test
		public void nullEventShouldThrowNullPointerException() throws IOException {
			assertThrows(NullPointerException.class, () -> {
				EditUserViewTest.this.userEditView.uploadImage(null);
			});
		}

		@Test
		public void uploadImageShouldSetImageNameToUserObject() throws IOException {
			final User user = EditUserViewTest.this.mockUser();
			EditUserViewTest.this.userEditView.setUser(user);

			final FileUploadEvent event = mock(FileUploadEvent.class);
			final UploadedFile file = mock(UploadedFile.class);
			when(event.getFile()).thenReturn(file);
			final InputStream is = mock(InputStream.class);
			when(event.getFile().getInputstream()).thenReturn(is);
			when(EditUserViewTest.this.fileUploadService.uploadImage(is)).thenReturn("uuidImageName");

			EditUserViewTest.this.userEditView.uploadImage(event);
			assertEquals("uuidImageName", user.getImageFileName());
		}
	}

	@Nested
	class ValidateEmail {

		@Test
		public void changeEmailToDuplicatedEmailShouldThrowValidatorException() {
			assertThrows(ValidatorException.class, () -> {
				EditUserViewTest.this.userEditView.setUser(EditUserViewTest.this.mockUser());

				when(EditUserViewTest.this.userService.isEmailInUse("bernard@test.com")).thenReturn(true);
				when(EditUserViewTest.this.translator.message("user.error.email_exists")).thenReturn("Error message");

				EditUserViewTest.this.userEditView.validateEmail(null, null, "bernard@test.com");
			});
		}

		@Test
		public void changeEmailToNonDuplicatedEmailShoulbBeOk() {
			EditUserViewTest.this.userEditView.setUser(EditUserViewTest.this.mockUser());
			when(EditUserViewTest.this.userService.isEmailInUse("another.email@test.com")).thenReturn(false);
			EditUserViewTest.this.userEditView.validateEmail(null, null, "another.email@test.com");
		}

		@Test
		public void nullEmailShouldBeOk() {
			EditUserViewTest.this.userEditView.validateEmail(null, null, null);
		}

		@Test
		public void unmodifiedEmailShouldBeOk() {
			EditUserViewTest.this.userEditView.setUser(EditUserViewTest.this.mockUser());
			EditUserViewTest.this.userEditView.validateEmail(null, null, "abel@test.com");
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

	private EditUserView userEditView;

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
		this.userEditView = new EditUserView();
		this.userEditView.setFileUploadService(this.fileUploadService);
		this.userEditView.setUserService(this.userService);
		this.userEditView.setFlash(this.flash);
		this.userEditView.setTranslator(this.translator);
		this.userEditView.setSessionUser(this.sessionUser);
	}
}
