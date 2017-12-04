package es.uc3m.tiw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uc3m.tiw.dao.CategoryDAO;
import es.uc3m.tiw.dao.EventDAO;
import es.uc3m.tiw.dao.UserDAO;
import es.uc3m.tiw.entities.Category;
import es.uc3m.tiw.entities.Event;
import es.uc3m.tiw.entities.User;

@RestController
@CrossOrigin
public class Controller {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	EventDAO eventDAO;
	
	@Autowired
	CategoryDAO categoryDAO;
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public List<User> getUsers(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			return userDAO.findByEmailAndPassword(email, password);
		} else {
			return userDAO.findAll();
		}
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/users/{id}")
	public User getUserById(@PathVariable Long id){
		return userDAO.findOne(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.POST, value="/users")
	public User saveUser(@RequestBody @Validated User user){
		return userDAO.save(user);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
	public void deleteUser(@PathVariable Long id){
		userDAO.delete(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody @Validated User user){
		User updateUs = userDAO.findOne(id);
		updateUs.setName(user.getName());
		updateUs.setSurename(user.getSurename());
		return userDAO.save(updateUs);
	}
	
	/**
	 * EVENTS
	 */
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/events")
	public List<Event> getEvents(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			return eventDAO.findAll();
		} else {
			return eventDAO.findAll();
		}
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/events/{id}")
	public Event getEventById(@PathVariable Long id){
		return eventDAO.findOne(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/events/{id}/user")
	public User getUserFromEvent(@PathVariable Long id){
		return userDAO.findByEventsId(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/events")
	public Event saveEvent(@RequestBody @Validated Event event){
		return eventDAO.save(event);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/events/{id}")
	public void deleteEvent(@PathVariable Long id){
		eventDAO.delete(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Event updateEvent(@PathVariable Long id, @RequestBody @Validated Event event){
		Event updateEv = eventDAO.findOne(id);
		updateEv.setDate(event.getDate());
		updateEv.setDescription(event.getDescription());
		updateEv.setImage(event.getImage());
		updateEv.setLocation(event.getLocation());
		updateEv.setPrice(event.getPrice());
		updateEv.setRoom(event.getRoom());
		updateEv.setTickets(event.getTickets());
		updateEv.setTime(event.getTime());
		updateEv.setTitle(event.getTitle());
		return eventDAO.save(updateEv);
	}
	
	/**
	 * 
	 * CATEGORIES
	 */
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/categories")
	public List<Category> getCategories(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			return categoryDAO.findAll();
		} else {
			return categoryDAO.findAll();
		}
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/categories/{name}")
	public Category getCategoryById(@PathVariable String name){
		return categoryDAO.findByName(name);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/categories/{name}/events")
	public List<Event> getEventsFromCategory(@PathVariable String name){
		return eventDAO.findByCategoryBeanName(name);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.POST, value="/categories")
	public Category saveCategory(@RequestBody @Validated Category category){
		return categoryDAO.save(category);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.DELETE, value="/categories/{name}")
	public void deleteCategory(@PathVariable String name){
		categoryDAO.delete(categoryDAO.findByName(name));
	}
	
	//??? Not working properly
	@RequestMapping(method=RequestMethod.PUT, value="/categories/{name}")
	public Category updateCategory(@PathVariable String name, @RequestBody @Validated Category category){
		Category updateCat = categoryDAO.findByName(name);
		updateCat.setName(category.getName());
		return categoryDAO.save(updateCat);
	}
}
