package ged.web.view.job.converter;

import java.util.List;

import javax.faces.convert.FacesConverter;

import ged.ejb.service.job.ContractDuration;
import ged.web.core.view.AbstractLookupEntityConverter;

@FacesConverter(forClass = ContractDuration.class)
public class ContractDurationConverter extends
		AbstractLookupEntityConverter<ContractDuration> {

	@Override
	protected List<ContractDuration> getListElements() {
		return getAppBean().getContractDurations();
	}

}
