package es.uc3m.tiw.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import es.uc3m.tiw.dao.ReceiptDAO;
import es.uc3m.tiw.dao.TicketDAO;
import es.uc3m.tiw.dao.UserDAO;
import es.uc3m.tiw.entities.Category;
import es.uc3m.tiw.entities.Event;
import es.uc3m.tiw.entities.Receipt;
import es.uc3m.tiw.entities.Ticket;
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
	
	@Autowired
	TicketDAO ticketDAO;
	
	@Autowired
	ReceiptDAO receiptDAO;
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users")
	public List<User> getUsers(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			return userDAO.findByEmailAndPassword(email, password);
		} else {
			return userDAO.findAll();
		}
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{id}")
	public User getUserById(@PathVariable Long id){
		return userDAO.findOne(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.POST, value="/api/users")
	public User saveUser(@RequestBody @Validated User user){
		return userDAO.save(user);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.DELETE, value="/api/users/{id}")
	public void deleteUser(@PathVariable Long id){
		userDAO.delete(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.PUT, value="/api/users/{id}")
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
	@RequestMapping(method=RequestMethod.GET, value="/api/events")
	public List<Event> getEvents(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			//Implementar filtros de búsqueda
			return eventDAO.findAll();
		} else {
			return eventDAO.findAll();
		}
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/events/{id}")
	public Event getEventById(@PathVariable Long id){
		return eventDAO.findOne(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/events/{id}/user")
	public User getUserFromEvent(@PathVariable Long id){
		return userDAO.findByEventsId(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.POST, value="/api/events")
	public Event saveEvent(@RequestParam(value="category",required=true) String category, @RequestParam(value="user",required=true) Long userId,@RequestBody @Validated Event event){
		event.setCategoryBean(categoryDAO.findByName(category));
		event.setUser(userDAO.findOne(userId));
		return eventDAO.save(event);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/events/{id}")
	public void deleteEvent(@PathVariable Long id){
		eventDAO.delete(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/api/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Event updateEvent(@RequestParam(value="category",required=true) String category, @RequestParam(value="user",required=true) Long userId,@PathVariable Long id, @RequestBody @Validated Event event){
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
		updateEv.setCategoryBean(categoryDAO.findByName(category));
		updateEv.setUser(userDAO.findOne(userId));
		return eventDAO.save(updateEv);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{id}/events")
	public List<Event> getEventsFromUser(@PathVariable Long id){
		return eventDAO.findByUserId(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/events/{eventid}")
	public Event getEventFromUser(@PathVariable Long userid, @PathVariable Long eventid){
		return eventDAO.findByIdAndUserId(userid, eventid);
	}

	/**
	 * 
	 * CATEGORIES
	 */
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/categories")
	public List<Category> getCategories(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			return categoryDAO.findAll();
		} else {
			return categoryDAO.findAll();
		}
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/categories/{name}")
	public Category getCategoryById(@PathVariable String name){
		return categoryDAO.findByName(name);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/categories/{name}/events")
	public List<Event> getEventsFromCategory(@PathVariable String name){
		return eventDAO.findByCategoryBeanName(name);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/categories/{name}/events/{id}")
	public Event getEventFromCategory(@PathVariable String name, @PathVariable Long id){
		return eventDAO.findByIdAndCategoryBeanName(id, name);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.POST, value="/api/categories")
	public Category saveCategory(@RequestBody @Validated Category category){
		return categoryDAO.save(category);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.DELETE, value="/api/categories/{name}")
	public void deleteCategory(@PathVariable String name){
		categoryDAO.delete(categoryDAO.findByName(name));
	}
	
	
	/**
	 * 
	 * TICKETS and RECEIPTS
	 */
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/receipts")
	public List<Receipt> getReceiptsFromUser(@PathVariable Long userid){
		return receiptDAO.findByUserId(userid);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.POST, value="/api/users/{userid}/receipts")
	public Receipt saveReceipt(@RequestParam(value="event",required=true) Long eventid, @PathVariable Long userid, @RequestBody @Validated Receipt receipt){
		receipt.setUser(userDAO.findOne(userid));
		receipt.setEvent(eventDAO.findOne(eventid));
		return receiptDAO.save(receipt);
	}
		
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/receipts/{id}")
	public Receipt getReceiptFromUser(@PathVariable Long userid, @PathVariable Long id){
		return receiptDAO.findByIdAndUserId(id, userid);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.DELETE, value="/api/users/{userid}/receipts/{id}")
	public void deleteReceiptFromUser(@PathVariable Long userid, @PathVariable Long id){
		receiptDAO.delete(id);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/receipts/{id}/tickets")
	public List<Ticket> getTicketsFromUserReceipts(@PathVariable Long userid, @PathVariable Long id){
		return ticketDAO.findByReceiptIdAndReceiptUserId(id, userid);
	}
	
	//OK
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/tickets")
	public List<Ticket> getTicketsFromUser(@PathVariable Long userid){
		return ticketDAO.findByReceiptUserId(userid);
	}
}
