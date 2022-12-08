package es.uc3m.tiw.repository;

import es.uc3m.tiw.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserDAO extends CrudRepository<User, Long>{

	public List<User> findAll();

	public User findByName(String name);
	public User findByUsername(String username);
	
}
