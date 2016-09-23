package ged.web.core.view;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDialogBean extends AbstractPageBean {

	private static final Logger logger = Logger.getLogger(AbstractDialogBean.class.getName());

	private static final long serialVersionUID = 1432485776371482410L;

	protected boolean modal = true;

	protected boolean rendered = false;

	protected abstract void clear();

	public void hide() {
		logger.log(Level.FINE, "Hide change password dialog action performed");
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
		logger.log(Level.FINE, "Show change password dialog action performed");
		this.rendered = true;
	}
}
