package ged.web.core.view;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public abstract class AbstractDialogBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDialogBean.class.getName());

	private static final long serialVersionUID = 1432485776371482410L;

	protected boolean modal = true;

	protected boolean rendered = false;

	protected abstract void clear();

	public void hide() {
		logger.debug( "Hide dialog action performed");
		clear();
		this.rendered = false;
	}

	public boolean isModal() {
		return this.modal;
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void setModal(final boolean modal) {
		this.modal = modal;
	}

	public void setRendered(final boolean rendered) {
		this.rendered = rendered;
	}

	public void show() {
		logger.debug( "Show dialog action performed");
		this.rendered = true;
	}
}