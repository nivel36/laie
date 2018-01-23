package ged.api.v1.mapper;

import javax.enterprise.inject.Produces;

public class MapperResources {

	@Produces
	public UserMapper produceUserMapper() {
		final UserMapper mapper = new UserMapper();
		return mapper;
	}
}
