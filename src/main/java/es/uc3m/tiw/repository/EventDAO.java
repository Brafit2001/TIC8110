package es.uc3m.tiw.repository;

import es.uc3m.tiw.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EventDAO extends CrudRepository<Event, Long>{

	public List<Event> findAll();

	public List<Event> findByCategory(String name);
	
	public Event findByName(String name);
	
}
