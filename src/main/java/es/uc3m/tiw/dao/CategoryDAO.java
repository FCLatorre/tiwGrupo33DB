package es.uc3m.tiw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uc3m.tiw.entities.Category;


public interface CategoryDAO extends CrudRepository<Category, Long>{
	public List<Category> findAll();
	public Category findByName(String name);
}
