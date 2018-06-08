package ged.web.core.view;

import java.util.Objects;

import org.primefaces.PrimeFaces;

public abstract class AbstractDialogBean extends AbstractBean {

	private static final long serialVersionUID = 8427233134254733395L;

	public void closeDialog() {
		this.closeDialog(null);
	}

	public void closeDialog(final Object value) {
		PrimeFaces.current().dialog().closeDynamic(value);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(final String key) {
		Objects.requireNonNull(key);
		return (T) this.externalContext.getRequestParameterMap().get(key);
	}

	protected Long getIdFromParameters(final String idName) {
		try {
			final String idValue = this.getAttribute(idName);
			if (idValue == null) {
				return null;
			}
			return Long.parseLong(idValue);
		}
		catch (final NumberFormatException e) {
			throw new IllegalStateException();
		}
	}
}