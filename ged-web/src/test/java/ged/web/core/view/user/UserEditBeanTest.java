package ged.web.core.view.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.Flash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.core.FileUploadService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.web.core.util.Translator;
import ged.web.view.user.UserEditBean;

@ExtendWith(MockitoExtension.class)
public class UserEditBeanTest {

	@Mock
	private FileUploadService fileUploadService;

	@Mock
	private Flash flash;

	@Mock
	private Translator translator;

	private UserEditBean userEditBean;

	@Mock
	private UserService userService;

	@Test
	public void cancelEditUserTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		user.setId(1L);
		when(this.flash.containsKey("user")).thenReturn(true);
		when(this.flash.get("user")).thenReturn(user);
		this.userEditBean.init();
		final String url = this.userEditBean.cancel();
		assertEquals("/faces/user/user?faces-redirect=true&userId=1", url);
	}

	@Test
	public void cancelNewUserTest() {
		this.userEditBean.init();
		final String url = this.userEditBean.cancel();
		assertEquals("/faces/user/userSearch", url);
	}

	@Test
	public void cleanManagerTest() {
		final User user = new User();
		final User manager = new User();
		user.setManager(manager);
		this.userEditBean.setUser(user);

		this.userEditBean.cleanManager();
		assertNull(this.userEditBean.getUser().getManager());
	}

	@Test
	public void completeManagerNullTest() {
		final List<User> managers = this.userEditBean.completeManager(null);
		assertEquals(0, managers.size());
	}

	@Test
	public void completeManagerShortStringTest() {
		final List<User> managers = this.userEditBean.completeManager("as");
		assertEquals(0, managers.size());
	}

	@Test
	public void completeManagerTest() {
		final List<User> mockedManagers = this.mockListOfUsers();
		Mockito.when(this.userService.search("Abe")).thenReturn(mockedManagers);
		final List<User> managers = this.userEditBean.completeManager("Abe");
		assertEquals(1, managers.size());
		assertEquals("abel@test.com", managers.get(0).getEmail());
	}

	@Test
	public void getUserTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		final User returnedUser = this.userEditBean.getUser();
		assertEquals(user, returnedUser);
	}

	@Test
	public void initNewUserTest() {
		this.userEditBean.init();
		assertNull(this.userEditBean.getUser().getEmail());
	}

	@Test
	public void initTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		when(this.flash.containsKey("user")).thenReturn(true);
		when(this.flash.get("user")).thenReturn(user);
		this.userEditBean.init();
		assertEquals("abel@test.com", this.userEditBean.getUser().getEmail());
	}

	private List<User> mockListOfUsers() {
		final List<User> mockedManagers = new ArrayList<>();
		final User manager = new User();
		manager.setEmail("abel@test.com");
		mockedManagers.add(manager);
		return mockedManagers;
	}

	@Test
	public void saveInsertTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		final User savedUser = new User();
		savedUser.setEmail("abel@test.com");
		savedUser.setId(1L);
		Mockito.when(this.userService.save(user)).thenReturn(savedUser);
		this.userEditBean.save();
		assertEquals("abel@test.com", this.userEditBean.getUser().getEmail());
		assertEquals(1L, this.userEditBean.getUser().getId());
	}

	@Test
	public void saveUpdateTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		user.setId(1L);
		this.userEditBean.setUser(user);

		final User updatedUser = new User();
		updatedUser.setEmail("abel@test.com");
		updatedUser.setId(1L);

		Mockito.when(this.userService.save(user)).thenReturn(updatedUser);

		this.userEditBean.save();

		assertEquals("abel@test.com", updatedUser.getEmail());
		assertEquals(1L, updatedUser.getId());
	}

	@BeforeEach
	public void setUp() {
		this.userEditBean = new UserEditBean();
		this.userEditBean.setFileUploadService(this.fileUploadService);
		this.userEditBean.setUserService(this.userService);
		this.userEditBean.setFlash(this.flash);
		this.userEditBean.setTranslator(this.translator);
	}

	@Test
	public void uploadImageEmptyFileTest() throws IOException {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		final FileUploadEvent event = Mockito.mock(FileUploadEvent.class);
		this.userEditBean.uploadImage(event);
		assertNull(user.getImageFileName());
	}

	@Test
	public void uploadImageNullTest() throws IOException {
		
		this.userEditBean.uploadImage(null);
	}

	@Test
	public void uploadImageTest() throws IOException {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		final FileUploadEvent event = mock(FileUploadEvent.class);
		final UploadedFile file = mock(UploadedFile.class);
		Mockito.when(event.getFile()).thenReturn(file);
		final InputStream is = mock(InputStream.class);
		Mockito.when(event.getFile().getInputstream()).thenReturn(is);
		Mockito.when(this.fileUploadService.uploadImage(is)).thenReturn("a0");

		this.userEditBean.uploadImage(event);
		assertEquals("a0", user.getImageFileName());
	}

	@Test
	public void validateEmailDuplicatedTest() {
		final User user = new User();
		user.setEmail("blai@test.com");
		this.userEditBean.setUser(user);
		when(this.userService.emailExists("abel@test.com")).thenReturn(true);
		when(this.translator.message("user.error.email_exists")).thenReturn("Error message");
		this.userEditBean.validateEmail(null, null, "abel@test.com");
	}

	@Test
	public void validateEmailEqualTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		when(this.userService.emailExists("abel@test.com")).thenReturn(true);
		this.userEditBean.validateEmail(null, null, "abel@test.com");
	}

	@Test
	public void validateEmailNullTest() {
		this.userEditBean.validateEmail(null, null, null);
	}

	@Test
	public void validateEmailTest() {
		final User user = new User();
		user.setEmail("blai@test.com");
		this.userEditBean.setUser(user);
		when(this.userService.emailExists("abel@test.com")).thenReturn(false);
		this.userEditBean.validateEmail(null, null, "abel@test.com");
	}

	@Test
	public void validateRoleEqualsTest() {
		final User user = new User();
		final Role admin = new Role();
		admin.setName("ADMIN");
		user.setRole(admin);
		this.userEditBean.setUser(user);
		final User manager = new User();
		manager.setEmail("abel@test.com");
		manager.setRole(admin);
		user.setManager(manager);
		this.userEditBean.validateRole(null, null, admin);
	}

	@Test
	public void validateRoleInvalidTest() {
		final Role admin = new Role();
		admin.setName("ADMIN");

		final Role subordinate = new Role();
		subordinate.setName("SUBORDINATE_ROLE");

		final User manager = new User();
		manager.setRole(subordinate);

		final User user = new User();
		user.setManager(manager);
		manager.setEmail("abel@test.com");

		this.userEditBean.setUser(user);
		when(this.translator.message("user.error.role")).thenReturn("Error message");
		when(this.userService.isASubordinateRole(subordinate, admin)).thenReturn(false);
		
		this.userEditBean.validateRole(null, null, admin);
	}

	@Test
	public void validateRoleNullTest() {
		this.userEditBean.validateRole(null, null, null);
	}

	@Test
	public void validateRoleValidTest() {
		final Role admin = new Role();
		admin.setName("ADMIN");

		final Role subordinate = new Role();
		subordinate.setName("SUBORDINATE_ROLE");

		final User manager = new User();
		manager.setRole(admin);
		manager.setEmail("abel@test.com");

		final User user = new User();
		user.setRole(admin);
		this.userEditBean.setUser(user);

		user.setManager(manager);
		Mockito.when(this.userService.isASubordinateRole(admin, subordinate)).thenReturn(true);
		this.userEditBean.validateRole(null, null, subordinate);
	}

	@Test
	public void validateRoleWithoutManagerTest() {
		final User user = new User();
		this.userEditBean.setUser(user);
		this.userEditBean.validateRole(null, null, new Role());
	}
}
