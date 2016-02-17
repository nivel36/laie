package ged.ejb.service.candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class CandidateService extends GenericServiceImpl {

	public Candidate searchById(long id) {
		return getByPrimaryKey(Candidate.class, id);
	}

	public Candidate searchCandidateAndCurriculumById(long id) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("id", id);
		Candidate candidate = getByTypedQuerySingleResult(Candidate.class,
				"Candidate.findById", properties);
		return candidate;
	}

	public List<Candidate> searchByProperties(String name,
			String surename, String email, String phoneNumber) {
		List<Candidate> candidates = null;
		Map<String, Object> properties = new HashMap<String, Object>();
		addSearchProperty("name", name, properties);
		addSearchProperty("surename", surename, properties);
		addSearchProperty("email", email, properties);
		addSearchProperty("phoneNumber", phoneNumber, properties);
		candidates = getByProperties(Candidate.class, properties, 10, 0);
		return candidates;
	}

	private void addSearchProperty(String name, String value,
			Map<String, Object> properties) {
		if (value != null && !value.trim().equals("")) {
			properties.put(name, value);
		}
	}
}
