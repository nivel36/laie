package es.nivel36.laie.web.core.view.component;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.SelectEvent;

import es.nivel36.laie.ejb.user.User;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.NamingContainer;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UINamingContainer;

@FacesComponent(value = "inputUsers")
public class InputUsers extends UIInput implements NamingContainer {

	private UIInput input;

	@SuppressWarnings("unchecked")
	private void addValueToList(final User user) {
		Object value = this.input.getValue();
		if (value == null) {
			value = new ArrayList<Object>();
		}
		((List<Object>) value).add(user);
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInput() {
		return this.input;
	}

	public void onDialogReturn(final SelectEvent<User> event) {
		final User user = event.getObject();
		this.addValueToList(user);
	}

	public void setInput(final UIInput input) {
		this.input = input;
	}
}