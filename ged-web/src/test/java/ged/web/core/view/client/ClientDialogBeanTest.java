package ged.web.core.view.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ged.ejb.client.ClientService;
import ged.web.core.view.SessionUser;
import ged.web.view.client.ClientEditBean;

@ExtendWith(MockitoExtension.class)
public class ClientDialogBeanTest {

	private ClientEditBean clientDialogBean;

	@Mock
	private ClientService clientService;

	@Mock
	private SessionUser sessionBean;

	@BeforeEach
	public void setUp() {
		this.clientDialogBean = new ClientEditBean();
		this.clientDialogBean.setClientService(this.clientService);
		this.clientDialogBean.setSessionBean(this.sessionBean);
	}
}
