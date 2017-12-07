package es.uc3m.tiw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uc3m.tiw.entities.Category;
import es.uc3m.tiw.entities.Event;


public interface EventDAO extends CrudRepository<Event, Long>{
	public List<Event> findAll();
	public Event findByIdAndUserId(Long eventid, Long userid);
	public Event findByIdAndCategoryBeanName(Long id, String name);
	public List<Event> findByCategoryBeanName(String name);
	public List<Event> findByUserId(Long id);
}
