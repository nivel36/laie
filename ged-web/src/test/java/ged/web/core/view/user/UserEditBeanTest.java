package ged.web.core.view.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.Flash;
import javax.faces.validator.ValidatorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import ged.ejb.core.FileUploadService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.core.util.Translator;
import ged.web.view.user.UserEditBean;

@RunWith(MockitoJUnitRunner.class)
public class UserEditBeanTest {

	@Mock
	private FileUploadService fileUploadService;

	@Mock
	private Flash flash;

	@Mock
	private RoleService roleService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private Translator translator;

	private UserEditBean userEditBean;

	@Mock
	private UserService userService;

	@Test
	public void completeManagerNullTest() {
		final List<User> managers = this.userEditBean.completeManager(null);
		Assert.assertEquals(0, managers.size());
	}

	@Test
	public void completeManagerShortStringTest() {
		final List<User> managers = this.userEditBean.completeManager("as");
		Assert.assertEquals(0, managers.size());
	}

	@Test
	public void completeManagerTest() {
		final List<User> mockedManagers = this.mockListOfUsers();
		Mockito.when(this.userService.search("Abe")).thenReturn(mockedManagers);
		final List<User> managers = this.userEditBean.completeManager("Abe");
		Assert.assertEquals(1, managers.size());
		Assert.assertEquals("abel@test.com", managers.get(0).getEmail());
	}

	@Test
	public void getUserTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		final User returnedUser = this.userEditBean.getUser();
		Assert.assertEquals(user, returnedUser);
	}

	@Test
	public void initNewUserTest() {
		this.userEditBean.init();
		Assert.assertNull(this.userEditBean.getUser().getEmail());
	}

	@Test
	public void initTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		Mockito.when(this.flash.containsKey("user")).thenReturn(true);
		Mockito.when(this.flash.get("user")).thenReturn(user);
		this.userEditBean.init();
		Assert.assertEquals("abel@test.com", this.userEditBean.getUser().getEmail());
	}

	@Test
	public void isNewUserFalseTest() {
		final User user = new User();
		user.setId(1L);
		this.userEditBean.setUser(user);
		final boolean newUser = this.userEditBean.isNewUser();
		Assert.assertEquals(false, newUser);
	}

	@Test
	public void isNewUserNullUserTest() {
		this.thrown.expect(IllegalStateException.class);
		this.userEditBean.isNewUser();
	}

	@Test
	public void isNewUserTrueTest() {
		final User user = new User();
		this.userEditBean.setUser(user);
		final boolean newUser = this.userEditBean.isNewUser();
		Assert.assertEquals(true, newUser);
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

		Mockito.doAnswer(invocation -> {
			final Object[] args = invocation.getArguments();
			((User) args[0]).setId(1L);
			return null;
		}).when(this.userService).insert(user);

		this.userEditBean.save();
		Assert.assertEquals("abel@test.com", user.getEmail());
		Assert.assertEquals(1L, user.getId());
	}

	@Test
	public void saveNullUserTest() {
		this.thrown.expect(IllegalStateException.class);
		this.userEditBean.save();
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

		Mockito.when(this.userService.update(user)).thenReturn(updatedUser);

		this.userEditBean.save();

		Assert.assertEquals("abel@test.com", updatedUser.getEmail());
		Assert.assertEquals(1L, updatedUser.getId());
	}

	@Before
	public void setUp() {
		this.userEditBean = new UserEditBean();
		this.userEditBean.setFileUploadService(this.fileUploadService);
		this.userEditBean.setRoleService(this.roleService);
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
		Assert.assertNull(user.getImageFileName());
	}

	@Test
	public void uploadImageNullTest() throws IOException {
		this.thrown.expect(NullPointerException.class);
		this.userEditBean.uploadImage(null);
	}

	@Test
	public void uploadImageTest() throws IOException {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		final FileUploadEvent event = Mockito.mock(FileUploadEvent.class);
		final UploadedFile file = Mockito.mock(UploadedFile.class);
		Mockito.when(event.getFile()).thenReturn(file);
		final InputStream is = Mockito.mock(InputStream.class);
		Mockito.when(event.getFile().getInputstream()).thenReturn(is);
		Mockito.when(this.fileUploadService.uploadImage(is)).thenReturn("a0");

		this.userEditBean.uploadImage(event);
		Assert.assertEquals("a0", user.getImageFileName());
	}

	@Test
	public void validateEmailDuplicatedTest() {
		final User user = new User();
		user.setEmail("blai@test.com");
		this.userEditBean.setUser(user);
		Mockito.when(this.userService.emailExists("abel@test.com")).thenReturn(true);
		Mockito.when(this.translator.message("user.error.email_exists")).thenReturn("Error message");
		this.thrown.expect(ValidatorException.class);
		this.userEditBean.validateEmail(null, null, "abel@test.com");
	}

	@Test
	public void validateEmailEqualTest() {
		final User user = new User();
		user.setEmail("abel@test.com");
		this.userEditBean.setUser(user);
		Mockito.when(this.userService.emailExists("abel@test.com")).thenReturn(true);
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
		Mockito.when(this.userService.emailExists("abel@test.com")).thenReturn(false);
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
		Mockito.when(this.translator.message("user.error.role")).thenReturn("Error message");
		Mockito.when(this.roleService.isASubordinateRole(subordinate, admin)).thenReturn(false);
		this.thrown.expect(ValidatorException.class);
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
		Mockito.when(this.roleService.isASubordinateRole(admin, subordinate)).thenReturn(true);
		this.userEditBean.validateRole(null, null, subordinate);
	}

	@Test
	public void validateRoleWithoutManagerTest() {
		final User user = new User();
		this.userEditBean.setUser(user);
		this.userEditBean.validateRole(null, null, new Role());
	}
}
