package ged.web.view.user;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class UserSearchPopupBean extends AbstractUserSearchBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 9150785979243375541L;

	protected boolean rendered = false;

	public void cancel() {
		hide();
	}

	public void hide() {
		cleanSearchFields();
		this.rendered = false;
	}

	@Override
	@PostConstruct
	public void init() {
		logger.trace( "Init UserSearchBean");
		if (isRendered()) {
			search();
		}
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void show() {
		this.rendered = true;
		search();
	}
}