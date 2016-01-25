package ged.web.view.job.converter;

import java.util.List;

import javax.faces.convert.FacesConverter;

import ged.ejb.service.job.ContractType;
import ged.web.core.view.AbstractLookupEntityConverter;

@FacesConverter(forClass = ContractType.class)
public class ContractTypeConverter extends
		AbstractLookupEntityConverter<ContractType> {

	@Override
	protected List<ContractType> getListElements() {
		return getAppBean().getContractTypes();
	}
}