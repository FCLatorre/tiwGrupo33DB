package es.uc3m.tiw.controllers;

import java.util.ArrayList;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	
	@RequestMapping(method=RequestMethod.GET, value="/api/users")
	@ResponseBody
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		
		List<User> usersListByEmailAndPass = userDAO.findByEmailAndPassword(email, password);
		
		List<User> usersListNoParameter = userDAO.findAll();
		
		if(email != null && password != null) {

			return ResponseEntity.ok(usersListByEmailAndPass);

		} else {

			return ResponseEntity.ok(usersListNoParameter);

		}

	}

	
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{id}")
	@ResponseBody
	public ResponseEntity <User> getUserById(@PathVariable Long id){
		
		User userById = userDAO.findOne(id);
		
		if(id!=null){
			
			return ResponseEntity.ok(userById);
			
		}
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

	}

	
	@RequestMapping(method=RequestMethod.POST, value="/api/users")
	@ResponseBody
	public ResponseEntity<User> saveUser(@RequestBody @Validated User user){
		
		User userSaved = userDAO.save(user);
		
		if(user!=null){
			
			//return ResponseEntity.ok(userSaved);
			return new ResponseEntity<User>(userSaved,HttpStatus.CREATED);
		
		}
		
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

	}

	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/users/{id}")
	@ResponseBody
	public void deleteUser(@PathVariable Long id){
		
		if(id!=null){
			
			userDAO.delete(id);
			ResponseEntity.status(HttpStatus.NO_CONTENT);
			
		}
		
		ResponseEntity.status(HttpStatus.NOT_FOUND);

	}
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/api/users/{id}")
	@ResponseBody
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Validated User user){

		User updateUs = userDAO.findOne(id);
		User saveUpdateUs = userDAO.save(updateUs);
		
		if(id!=null){
			
			updateUs.setName(user.getName());

			updateUs.setSurename(user.getSurename());
			
			return ResponseEntity.ok(saveUpdateUs);
		
		}

		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

	}
	

	/**

	 * EVENTS

	 */

	
	@RequestMapping(method=RequestMethod.GET, value="/api/events")
	@ResponseBody
	public ResponseEntity<List<Event>> getEvents(@RequestParam(value="query",required=false) String query){

		List<Event> allEvents = eventDAO.findAll();
		
		if(query != null) {

			List<Event> returning = new ArrayList<Event>();

			List<Event> events = eventDAO.findAll();

			for(Event ev : events){

				if((ev.getTitle().contains(query) || ev.getDescription().contains(query) || ev.getDate().toString().contains(query) || ev.getLocation().contains(query) || ev.getRoom().contains(query) || ev.getCategoryBean().getName().contains(query)))

					returning.add(ev);

			}

			return ResponseEntity.ok(returning);

		} else {

			return ResponseEntity.ok(allEvents);

		}

	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/events/{id}")
	@ResponseBody
	public ResponseEntity<Event> getEventById(@PathVariable Long id){
		
		Event eventById = eventDAO.findOne(id);
		
		if(id!=null){
			
			return ResponseEntity.ok(eventById);
			
		}
		
		return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);

	}

	
	@RequestMapping(method=RequestMethod.GET, value="/api/events/{id}/user")
	@ResponseBody
	public ResponseEntity <User> getUserFromEvent(@PathVariable Long id){
		
		User userFromEvent = userDAO.findByEventsId(id);
		
		if(id!=null){
			
			return ResponseEntity.ok(userFromEvent);
			
		}
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

	}

	
	@RequestMapping(method=RequestMethod.POST, value="/api/events")

	public ResponseEntity <Event> saveEvent(@RequestParam(value="category",required=true) String category, @RequestParam(value="user",required=true) Long userId,@RequestBody @Validated Event event){
		
		Event eventSaved = eventDAO.save(event);
		
		if(event!=null){
			
			event.setCategoryBean(categoryDAO.findByName(category));

			event.setUser(userDAO.findOne(userId));
			
			//return ResponseEntity.ok(eventSaved);
			return new ResponseEntity<Event>(eventSaved,HttpStatus.CREATED);
		}

		return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);

	}

	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/events/{id}")
	@ResponseBody
	public void deleteEvent(@PathVariable Long id){
		
		if(id!=null){
			
			eventDAO.delete(id);
			ResponseEntity.status(HttpStatus.NO_CONTENT);
			
		}
		
		ResponseEntity.status(HttpStatus.NOT_FOUND);

	}


	@RequestMapping(method=RequestMethod.PUT, value="/api/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity <Event> updateEvent(@RequestParam(value="category",required=true) String category, @RequestParam(value="user",required=true) Long userId,@PathVariable Long id, @RequestBody @Validated Event event){
		
		Event updateEv = eventDAO.findOne(id);
		Event saveUpdateEv = eventDAO.save(updateEv);
		
		if(id!=null){
			
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
			
			return ResponseEntity.ok(saveUpdateEv);
		}

		return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);

	}

	
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{id}/events")
	@ResponseBody
	public ResponseEntity <List<Event>> getEventsFromUser(@PathVariable Long id){
		
		List<Event> eventsUserById = eventDAO.findByUserId(id);
		
		if(id!=null){
			
			return ResponseEntity.ok(eventsUserById);
			
		}
		
		return new ResponseEntity<List<Event>>(HttpStatus.NOT_FOUND);

	}
	

	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/events/{eventid}")

	public ResponseEntity <Event> getEventFromUser(@PathVariable Long userid, @PathVariable Long eventid){
		
		Event eventFromUser = eventDAO.findByIdAndUserId(userid, eventid);
		
		if(userid!=null && eventid!=null){
			
			return ResponseEntity.ok(eventFromUser);
			
		}
		
		return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);

	}
	

	/**

	 * 

	 * CATEGORIES

	 */

	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/categories")
	@ResponseBody
	public ResponseEntity<List<Category>> getCategories(@RequestParam(value="query",required=false) String query){
		
		List<Category> allCategories = categoryDAO.findAll();
		
		if(query != null) {
			
			List<Category> returning = new ArrayList<Category>();
			
			List<Category> categories = categoryDAO.findAll();
			
			for(Category cat : categories){

				if((cat.getName().contains(query))||(cat.getEvents().contains(query)))

					returning.add(cat);

			}

			return ResponseEntity.ok(returning);

		} else {

			return ResponseEntity.ok(allCategories);

		}

	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/categories/{name}")
	@ResponseBody
	public ResponseEntity<Category> getCategoryById(@PathVariable String name){
		
		Category categoryById = categoryDAO.findByName(name);
		
		if(name!=null){
			
			return ResponseEntity.ok(categoryById);
			
		}
		return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);

	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/categories/{name}/events")
	@ResponseBody
	public ResponseEntity<List<Event>> getEventsFromCategory(@PathVariable String name){
		
		List<Event> eventsFromCategory = eventDAO.findByCategoryBeanName(name);
		
		if(name!=null){
			
			return ResponseEntity.ok(eventsFromCategory);
			
		}
		return new ResponseEntity<List<Event>>(HttpStatus.NOT_FOUND);

	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/categories/{name}/events/{id}")
	@ResponseBody
	public ResponseEntity<Event> getEventFromCategory(@PathVariable String name, @PathVariable Long id){
		
		Event eventFromCategory = eventDAO.findByIdAndCategoryBeanName(id, name);
		
		if(id!=null && name!=null){
			
			return ResponseEntity.ok(eventFromCategory);
			
		}
		return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);

	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/categories")
	@ResponseBody
	public ResponseEntity<Category> saveCategory(@RequestBody @Validated Category category){
		
		Category categorySaved = categoryDAO.save(category);
		
		if(category!=null){
			
			//return ResponseEntity.ok(categorySaved);
			return new ResponseEntity<Category>(categorySaved,HttpStatus.CREATED);
			
		}
		
		return new ResponseEntity<Category>(HttpStatus.BAD_REQUEST);

	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/categories/{name}")
	@ResponseBody
	public void deleteCategory(@PathVariable String name){
		
		if(name!=null){
			
			categoryDAO.delete(categoryDAO.findByName(name));
			ResponseEntity.status(HttpStatus.NO_CONTENT);
			
		}
		
		ResponseEntity.status(HttpStatus.NOT_FOUND);
		
	}

	

	/**

	 * 

	 * TICKETS and RECEIPTS

	 */

	
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/receipts")
	@ResponseBody
	public ResponseEntity<List<Receipt>> getReceiptsFromUser(@PathVariable Long userid){
		
		List<Receipt> receiptsFromUser = receiptDAO.findByUserId(userid);
		
		if(userid!=null){
			
			return ResponseEntity.ok(receiptsFromUser);
		
		}
		return new ResponseEntity<List<Receipt>>(HttpStatus.NOT_FOUND);

	}
	

	/*saveReceipt deleted, only available through Banco's Controller*/

	/*getReceiptFromUser deleted, only available through Banco's Controller*/	
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/users/{userid}/receipts/{id}")
	@ResponseBody
	public void deleteReceiptFromUser(@PathVariable Long userid, @PathVariable Long id){
		
		if(userid!=null && id!=null){
			
			receiptDAO.delete(id);
			ResponseEntity.status(HttpStatus.NO_CONTENT);
		}
		
		ResponseEntity.status(HttpStatus.NOT_FOUND);
		
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/receipts/{id}/tickets")
	@ResponseBody
	public ResponseEntity<List<Ticket>> getTicketsFromUserReceipts(@PathVariable Long userid, @PathVariable Long id){
		
		List<Ticket> ticketsFromUserReceipts = ticketDAO.findByReceiptIdAndReceiptUserId(id, userid);
		
		if(id!=null && userid!=null){
			
			return ResponseEntity.ok(ticketsFromUserReceipts);
		}
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);

	}

	
	@RequestMapping(method=RequestMethod.POST, value="/api/users/{userid}/receipts/{id}/tickets")
	@ResponseBody
	public ResponseEntity<Ticket> saveTickets(@RequestParam(value="event",required=true) Long eventid, @PathVariable Long userid, @PathVariable Long id, @RequestBody @Validated Ticket ticket){
		
		Ticket ticketSaved = ticketDAO.save(ticket);
		
		if(id!=null && eventid!=null && userid!=null){
			
			ticket.setReceipt(receiptDAO.findOne(id));

			ticket.setEvent(eventDAO.findOne(eventid));
			
			//return ResponseEntity.ok(ticketSaved);
			return new ResponseEntity<Ticket>(ticketSaved,HttpStatus.CREATED);
			
		}

		return new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);

	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/users/{userid}/receipts/{receiptid}/tickets/{id}")
	@ResponseBody
	public void deleteTicket(@PathVariable Long userid, @PathVariable Long id){
		
		if(userid!=null && id!=null){
			
			ticketDAO.delete(id);
			ResponseEntity.status(HttpStatus.NO_CONTENT);
	
		}
	
	ResponseEntity.status(HttpStatus.NOT_FOUND);
	
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/users/{userid}/tickets")
	@ResponseBody
	public ResponseEntity <List<Ticket>> getTicketsFromUser(@PathVariable Long userid){
		
		List<Ticket> ticketsFromUser = ticketDAO.findByReceiptUserId(userid);
		
		if(userid!=null){
			
			return ResponseEntity.ok(ticketsFromUser);
			
		}
		
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);

	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/tickets")
	@ResponseBody
	public ResponseEntity<List<Ticket>> getAllTickets(@RequestParam(value="query",required=false) String query){

		List<Ticket> allTickets = ticketDAO.findAll();
		
		if(query != null) {

			List<Ticket> returning = new ArrayList<Ticket>();

			List<Ticket> tickets = ticketDAO.findAll();

			for(Ticket tick : tickets){

				if((tick.getName().contains(query) || tick.getEvent().toString().contains(query) || tick.getReceipt().toString().contains(query) || tick.getId().toString().contains(query)))

					returning.add(tick);

			}

			return ResponseEntity.ok(returning);

		} else {

			return ResponseEntity.ok(allTickets);

		}

	}

}
