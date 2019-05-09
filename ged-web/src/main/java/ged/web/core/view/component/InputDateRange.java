package ged.web.core.view.component;

import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

@FacesComponent(value = "inputDateRange")
public class InputDateRange extends UIInput implements NamingContainer {

	public boolean validateDates(final FacesContext context, final List<UIInput> components, final List<Object> values) {
		boolean inputStillWorking = false;
		Integer inputStartYear = null;
		Integer inputEndYear = null;

		final int size = components.size();
		for (int i = 0; i < size; i++) {
			final UIInput component = components.get(i);

			if (component.getId().equals("stillWorking")) {
				if (values.get(i) == null) {
					inputStillWorking = false;
				}
				else {
					inputStillWorking = (Boolean) values.get(i);
				}
			}
			else if (component.getId().equals("fromYear")) {
				inputStartYear = (Integer) values.get(i);
			}
			else if (component.getId().equals("toYear")) {
				inputEndYear = (Integer) values.get(i);
			}
		}

		if (inputStillWorking) {
			return true;
		}
		if ((inputStartYear != null) && (inputEndYear != null) && ((inputStartYear - inputEndYear) > 0)) {
			return false;
		}
		return true;
	}
}