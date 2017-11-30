package es.uc3m.tiw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uc3m.tiw.entities.Category;
import es.uc3m.tiw.entities.Event;


public interface EventDAO extends CrudRepository<Event, Long>{
	public List<Event> findAll();
	public List<Event> findByCategoryBeanName(String name);
}
