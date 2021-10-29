package es.nivel36.laie.ejb.job.offer;

import java.util.Objects;

import es.nivel36.laie.ejb.client.ClientDao;
import es.nivel36.laie.ejb.core.model.Merger;
import es.nivel36.laie.ejb.user.UserDao;

public class JobOfferMerger implements Merger<JobOffer, JobOfferDto>{
	
	private UserDao userDao;
	
	private ClientDao clientDao;
	
	public JobOfferMerger(final UserDao userDao, final ClientDao clientDao) {
		Objects.requireNonNull(userDao);
		Objects.requireNonNull(clientDao);
		this.userDao = userDao;
		this.clientDao = clientDao;
	}

	@Override
	public void merge(JobOffer entity, JobOfferDto dto) {

		entity.setClient(client);
		entity.setOwner(user);
		entity.setTitle(dto.getTitle());
		entity.setState(dto.getState());
		entity.setDateOpened(dto.getDateOpened());
		entity.setDescription(dto.getDescription());
		entity.setPlaces(dto.getPlaces());
		entity.setPublished(dto.isPublished());
		entity.setSalary(dto.getSalary());
	}
}
