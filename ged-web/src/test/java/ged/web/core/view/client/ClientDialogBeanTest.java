package ged.web.core.view.client;

import org.junit.Before;
import org.mockito.Mock;

import ged.ejb.client.ClientService;
import ged.web.core.view.SessionBean;
import ged.web.view.client.ClientEditBean;

public class ClientDialogBeanTest {

	private ClientEditBean clientDialogBean;

	@Mock
	private ClientService clientService;

	@Mock
	private SessionBean sessionBean;

	@Before
	public void setUp() {
		this.clientDialogBean = new ClientEditBean();
		this.clientDialogBean.setClientService(this.clientService);
		this.clientDialogBean.setSessionBean(this.sessionBean);
	}
}
