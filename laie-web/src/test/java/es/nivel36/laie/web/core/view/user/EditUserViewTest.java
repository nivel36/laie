package es.nivel36.laie.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.util.Translator;
import es.nivel36.laie.web.core.view.SessionUser;
import es.nivel36.laie.web.view.user.EditUserView;

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
	private FileService fileUploadService;

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
