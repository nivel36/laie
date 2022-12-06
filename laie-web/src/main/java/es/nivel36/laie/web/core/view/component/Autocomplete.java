package es.nivel36.laie.web.core.view.component;

import java.io.IOException;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.NamingContainer;
import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.context.FacesContext;

@FacesComponent(value = "autocomplete")
public class Autocomplete extends UIInput implements NamingContainer {

	private UICommand cleanButton;

	private UIInput input;

	public void clean() {
		this.input.setValue(null);
		this.hideCleanButton();
	}

	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		super.encodeBegin(context);
		this.processCleanButton();
	}

	public UICommand getCleanButton() {
		return this.cleanButton;
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInput() {
		return this.input;
	}

	private void hideCleanButton() {
		this.cleanButton.setRendered(false);
	}

	private boolean isInputValueSet() {
		return this.input.getValue() != null;
	}

	private void processCleanButton() {
		if (this.isInputValueSet()) {
			this.showCleanButton();
		}
		else {
			this.hideCleanButton();
		}
	}

	public void setCleanButton(final UICommand cleanButton) {
		this.cleanButton = cleanButton;
	}

	public void setInput(final UIInput input) {
		this.input = input;
	}

	private void showCleanButton() {
		this.cleanButton.setRendered(true);
	}
}
