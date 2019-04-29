package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;

@Named
@ViewScoped
public class EditClientBean extends AbstractClientBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	public Client getClient() {
		return this.client;
	}

	@PostConstruct
	public void init() {
		this.client = this.getValueFromFlash("client");
		if (this.client == null) {
			Navigate.to(PageEnum.CLIENT_SEARCH).doGet();
			return;
		}
	}

	public String save() {
		logger.debug("Save client action performed");
		this.client = this.clientService.save(this.client);
		return gotoClientPage();
	}

}