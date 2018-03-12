package ged.web.core.view.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ged.ejb.client.ClientService;
import ged.ejb.user.User;
import ged.web.core.view.SessionBean;
import ged.web.view.client.ClientDialogBean;

@RunWith(MockitoJUnitRunner.class)
public class ClientDialogBeanTest {

	private ClientDialogBean clientDialogBean;

	@Mock
	private ClientService clientService;

	@Mock
	private SessionBean sessionBean;

	@Test
	public void closeDialogTest() {
		final ActionEvent actionEvent = mock(ActionEvent.class);
		final UIComponent uiComponent = mock(UIComponent.class);
		when(actionEvent.getComponent()).thenReturn(uiComponent);
		when(uiComponent.getAttributes()).thenReturn(null);
		final User user = new User();
		user.setEmail("aaron@test.com");
		when(this.sessionBean.getUser()).thenReturn(user);

		this.clientDialogBean.closeDialog();
		assertNull(this.clientDialogBean.getClient());
		assertFalse(this.clientDialogBean.isShowDialog());
	}

	@Test
	public void openDialogTest() {
		final ActionEvent actionEvent = mock(ActionEvent.class);
		final UIComponent uiComponent = mock(UIComponent.class);
		when(actionEvent.getComponent()).thenReturn(uiComponent);
		when(uiComponent.getAttributes()).thenReturn(null);
		final User user = new User();
		user.setEmail("aaron@test.com");
		when(this.sessionBean.getUser()).thenReturn(user);

		this.clientDialogBean.openDialog(actionEvent);
		assertNotNull(this.clientDialogBean.getClient());
		assertEquals("aaron@test.com", this.clientDialogBean.getClient().getOwner().getEmail());
		assertTrue(this.clientDialogBean.isShowDialog());
	}

	@Before
	public void setUp() {
		this.clientDialogBean = new ClientDialogBean();
		this.clientDialogBean.setClientService(this.clientService);
		this.clientDialogBean.setSessionBean(this.sessionBean);
	}
}
