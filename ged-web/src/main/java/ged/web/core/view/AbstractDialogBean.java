package ged.web.core.view;

import java.util.Map;
import java.util.Objects;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 8427233134254733395L;

	private Map<String, Object> attributes;

	private boolean showDialog;

	public void closeDialog() {
		this.showDialog = false;
		this.dispose();
	}

	protected abstract void dispose();

	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(final String key) {
		Objects.requireNonNull(key);
		if (this.attributes.containsKey(key)) {
			return (T) this.attributes.get(key);
		}
		else {
			return null;
		}
	}

	protected abstract void init();

	public boolean isShowDialog() {
		return this.showDialog;
	}

	public void openDialog(final ActionEvent event) {
		this.showDialog = true;
		this.attributes = event.getComponent().getAttributes();
		this.init();
	}

	public void openDialog(final SelectEvent event) {
		this.showDialog = true;
		this.attributes = event.getComponent().getAttributes();
		this.init();
	}

	public void setShowDialog(final boolean showDialog) {
		this.showDialog = showDialog;
	}
}