package es.uc3m.tiw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uc3m.tiw.entities.User;


public interface UserDAO extends CrudRepository<User, Long>{
	public List<User> findByEmailAndPassword(String email, String password);
	public List<User> findAll();
}
