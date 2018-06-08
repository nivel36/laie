package ged.web.core.view.client;

import org.junit.Before;
import org.mockito.Mock;

import ged.ejb.client.ClientService;
import ged.web.core.view.SessionBean;
import ged.web.view.client.ClientDialogBean;

public class ClientDialogBeanTest {

	private ClientDialogBean clientDialogBean;

	@Mock
	private ClientService clientService;

	@Mock
	private SessionBean sessionBean;

	@Before
	public void setUp() {
		this.clientDialogBean = new ClientDialogBean();
		this.clientDialogBean.setClientService(this.clientService);
		this.clientDialogBean.setSessionBean(this.sessionBean);
	}
}
