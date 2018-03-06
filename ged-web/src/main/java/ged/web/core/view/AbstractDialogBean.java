package ged.web.core.view;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 8427233134254733395L;

	private boolean showDialog;

	public void closeDialog() {
		this.showDialog = false;
		dispose();
	}

	protected abstract void dispose();

	protected abstract void init();

	public boolean isShowDialog() {
		return this.showDialog;
	}

	public void openDialog() {
		this.showDialog = true;
		init();
	}

	public void setShowDialog(final boolean showDialog) {
		this.showDialog = showDialog;
	}
}