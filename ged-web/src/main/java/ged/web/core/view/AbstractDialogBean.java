package ged.web.core.view;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PreDestroy;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

import ged.web.core.CallbackListener;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 8427233134254733395L;

	private Map<String, Object> attributes;

	protected CallbackListener callback;

	private boolean showDialog;

	protected String updateField;

	public void closeDialog() {
		this.showDialog = false;
		this.dispose();
	}

	public void closeDialog(final Object data) {
		this.closeDialog();
	}

	protected abstract void dispose();

	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(final String key) {
		Objects.requireNonNull(key);
		return (T) this.attributes.get(key);
	}

	public void handleClose(final CloseEvent event) {
		this.closeDialog();
	}

	protected abstract void init();

	public boolean isShowDialog() {
		return this.showDialog;
	}

	public void openDialog(final ActionEvent event) {
		this.showDialog = true;
		this.attributes = event.getComponent().getAttributes();
		this.callback = this.getAttribute("callback");
		this.updateField = this.getAttribute("updateField");
		this.init();
	}

	public void openDialog(final SelectEvent event) {
		this.showDialog = true;
		this.attributes = event.getComponent().getAttributes();
		this.init();
	}

	@PreDestroy
	public void preDestroy() {
		this.callback = null;
	}

	public void setSelectClientActionCallback(final CallbackListener callback) {
		this.callback = callback;
	}

	public void setShowDialog(final boolean showDialog) {
		this.showDialog = showDialog;
	}
}