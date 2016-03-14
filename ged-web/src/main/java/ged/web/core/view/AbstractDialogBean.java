package ged.web.core.view;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 1432485776371482410L;

	protected boolean modal = true;

	protected boolean rendered = false;

	public abstract void clear();

	public void hide() {
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
		this.rendered = true;
	}
}
