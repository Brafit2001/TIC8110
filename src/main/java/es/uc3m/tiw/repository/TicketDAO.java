package es.uc3m.tiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RestResource;

import es.uc3m.tiw.entity.Ticket;


public interface TicketDAO extends CrudRepository<Ticket, Long>{

	public List<Ticket> findAll();
	
}
