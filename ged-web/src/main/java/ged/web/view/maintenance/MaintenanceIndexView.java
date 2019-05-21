package ged.web.view.maintenance;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class MaintenanceIndexView extends AbstractView {

	private static final String MAINTENANCE_EDIT = "maintenanceEdit";

	private static final long serialVersionUID = 3841966930769061075L;

	public String modifyContractType() {
		return MAINTENANCE_EDIT;
	}

	public String newContractType() {
		return MAINTENANCE_EDIT;
	}
}