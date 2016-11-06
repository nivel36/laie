package ged.web.view.maintenance;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.core.maintenance.EnumEntity;
import ged.ejb.core.maintenance.MaintenanceService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class MaintenanceEditBean extends AbstractPageBean {

	private static final long serialVersionUID = 3193783501437738942L;

	private String entityClass;

	private EnumEntity enumEntity;

	private MaintenanceService maintenanceService;

	public String cancel() {
		return "maintenanceIndex";
	}

	public void delete() {
		this.maintenanceService.delete(this.enumEntity);
	}

	@PostConstruct
	public void init() {
		this.entityClass = (String) this.flash.get("entityClass");
		Objects.requireNonNull(this.entityClass);
	}

	public void save() {
		this.maintenanceService.insert(this.enumEntity);
	}
}