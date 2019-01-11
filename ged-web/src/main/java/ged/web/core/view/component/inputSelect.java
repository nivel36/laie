package ged.web.core.view.component;

import java.io.IOException;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;

@FacesComponent(value = "inputSelect")
public class inputSelect extends UIInput implements NamingContainer {

	private UIInput inputHidden;

	private UIInput otherInputText;

	private UISelectOne selectInput;

	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		super.encodeBegin(context);
		if (this.selectInput == null) {
			return;
		}
		final Object value = this.inputHidden.getValue();
		if (value == null) {
			this.putValueInSelectInput(null);
			return;
		}
		if (this.isHiddenValueInSelectMenu((String) value)) {
			this.putValueInSelectInput((String) value);
		}
		else {
			this.putValueInOtherField((String) value);
		}
	}

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	public UIInput getInputHidden() {
		return this.inputHidden;
	}

	public UIInput getOtherInputText() {
		return this.otherInputText;
	}

	public UISelectOne getSelectInput() {
		return this.selectInput;
	}

	private boolean hasOtherValueSelected() {
		final Object selectValue = this.selectInput.getValue();
		if (selectValue == null) {
			return false;
		}
		return "other".equals(selectValue.toString());
	}

	private boolean isHiddenValueInSelectMenu(final String value) {
		final List<UIComponent> children = this.selectInput.getChildren();
		for (final UIComponent component : children) {
			if (component instanceof UISelectItems) {
				final UISelectItems selectItems = (UISelectItems) component;
				final List<?> objects = (List<?>) selectItems.getValue();
				for (final Object selectItem : objects) {
					if (selectItem.toString().equals(value)) {
						return true;
					}

				}
				return false;
			}
		}
		return false;
	}

	public void onChangeSelectValue() {
		if (this.hasOtherValueSelected()) {
			this.otherInputText.setRendered(true);
			this.otherInputText.setValue(null);
		}
		else {
			this.otherInputText.setRendered(false);
		}
	}

	@Override
	public void processUpdates(final FacesContext context) {
		if (this.selectInput != null) {
			this.putValueInHiddenField();
		}
		super.processUpdates(context);
	}

	private void putOtherValueInHiddenField() {
		final Object otherValue = this.otherInputText.getValue();
		if (otherValue == null) {
			this.inputHidden.setValue(null);
		}
		else {
			this.inputHidden.setValue(otherValue);
		}
	}

	private void putSelectValueInHiddenField() {
		final Object selectValue = this.selectInput.getValue();
		if (selectValue == null) {
			this.inputHidden.setValue(null);
		}
		else {
			this.inputHidden.setValue(selectValue.toString());
		}
	}

	private void putValueInHiddenField() {
		if (this.hasOtherValueSelected()) {
			this.putOtherValueInHiddenField();
		}
		else {
			this.putSelectValueInHiddenField();
		}
	}

	private void putValueInOtherField(final String value) {
		this.selectInput.setValue("other");
		this.otherInputText.setValue(value);
		this.otherInputText.setRendered(true);
	}

	private void putValueInSelectInput(final String value) {
		this.otherInputText.setRendered(false);
		this.selectInput.setValue(value);
		this.otherInputText.setValue(null);
	}

	public void setInputHidden(final UIInput inputHidden) {
		this.inputHidden = inputHidden;
	}

	public void setOtherInputText(final UIInput otherInputText) {
		this.otherInputText = otherInputText;
	}

	public void setSelectInput(final UISelectOne selectInput) {
		this.selectInput = selectInput;
	}
}
