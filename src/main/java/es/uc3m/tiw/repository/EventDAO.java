package es.uc3m.tiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RestResource;

import es.uc3m.tiw.entity.Event;


public interface EventDAO extends CrudRepository<Event, Long>{

	public List<Event> findAll();

	public List<Event> findByCategory(String name);
	
	public Event findByName(String name);
	
}
