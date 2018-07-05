package ged.web.core.view.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.core.FileUploadService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.RoleService;
import ged.web.view.user.UserEditBean;

@RunWith(MockitoJUnitRunner.class)
public class UserEditBeanTest {

	@Mock
	private transient FileUploadService fileUploadService;

	@Mock
	private transient RoleService roleService;

	private UserEditBean userEditBean;

	@Mock
	private transient UserService userService;

	@Test
	public void cancelInsertTest() {
		final User newUser = new User();
		this.userEditBean.setUser(newUser);
		this.userEditBean.cancel();
	}

	@Before
	public void setUp() {
		this.userEditBean = new UserEditBean();
		this.userEditBean.setFileUploadService(this.fileUploadService);
		this.userEditBean.setRoleService(this.roleService);
		this.userEditBean.setUserService(this.userService);
	}

}
