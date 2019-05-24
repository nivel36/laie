package ged.web.view.maintenance;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.maintenance.AbstractEnumEntity;
import ged.ejb.core.maintenance.MaintenanceService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class MaintenanceEditView extends AbstractView {

	private static final String MAINTENANCE_INDEX = "maintenanceIndex";

	private static final long serialVersionUID = 3193783501437738942L;

	private String entityClass;

	private AbstractEnumEntity enumEntity;

	@Inject
	private transient MaintenanceService maintenanceService;

	public String cancel() {
		return MAINTENANCE_INDEX;
	}

	public void delete() {
		this.maintenanceService.delete(this.enumEntity);
	}

	public String getEntityClass() {
		return this.entityClass;
	}

	@PostConstruct
	public void init() {
		this.entityClass = (String) this.flash.get("entityClass");
		Objects.requireNonNull(this.entityClass);
	}

	public void save() {
		this.enumEntity = this.maintenanceService.save(this.enumEntity);
	}
}