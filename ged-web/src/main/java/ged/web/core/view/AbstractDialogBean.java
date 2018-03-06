package ged.web.core.view;

import java.util.Map;
import java.util.Objects;

import javax.faces.event.ActionEvent;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 8427233134254733395L;

	private Map<String, Object> attributes;

	private boolean showDialog;

	public void closeDialog() {
		this.showDialog = false;
		dispose();
	}

	protected abstract void dispose();

	protected Object getAttribute(final String key) {
		Objects.requireNonNull(key);
		return this.attributes.get(key);
	}

	protected abstract void init();

	public boolean isShowDialog() {
		return this.showDialog;
	}

	public void openDialog(final ActionEvent event) {
		this.showDialog = true;
		this.attributes = event.getComponent().getAttributes();
		init();
	}

	public void setShowDialog(final boolean showDialog) {
		this.showDialog = showDialog;
	}
}