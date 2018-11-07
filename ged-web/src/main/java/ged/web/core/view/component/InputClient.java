package ged.web.core.view.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

//TODO: Bug -> Seleccionas un valor, lo eliminas,
// le das a guardar para que salga el error de valor requerido,
// le das a buscar y cierras el dialogo sin seleccionar un valor.
// El campo mostrará el primer valor seleccionado en el paso 1
@FacesComponent(value = "inputClient")
public class InputClient extends UIInput implements NamingContainer {

	private UICommand cleanButton;

	private UIInput input;

	public void clean() {
		this.input.setValue(null);
		hideCleanButton();
	}

	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		super.encodeBegin(context);
		processCleanButton();
	}

	public UICommand getCleanButton() {
		return cleanButton;
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInput() {
		return input;
	}

	private void hideCleanButton() {
		cleanButton.setRendered(false);
	}

	private boolean isInputValueSet() {
		return input.getValue() != null;
	}

	private void processCleanButton() {
		if (isInputValueSet()) {
			showCleanButton();
		}
		else {
			hideCleanButton();
		}
	}

	public void setCleanButton(final UICommand cleanButton) {
		this.cleanButton = cleanButton;
	}

	public void setInput(final UIInput input) {
		this.input = input;
	}

	private void showCleanButton() {
		cleanButton.setRendered(true);
	}
}
