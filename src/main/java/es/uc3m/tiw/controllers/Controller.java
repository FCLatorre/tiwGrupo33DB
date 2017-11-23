package es.uc3m.tiw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uc3m.tiw.dao.UserDAO;
import es.uc3m.tiw.entities.User;

@RestController
@CrossOrigin
public class Controller {
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public List<User> getUsers(@RequestParam(value="email",required=false) String email, @RequestParam(value="password",required=false) String password){
		if(email != null && password != null) {
			return userDAO.findByEmailAndPassword(email, password);
		} else {
			return userDAO.findAll();
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/users/{id}")
	public User getUserById(@PathVariable Long id){
		return userDAO.findOne(id);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/users")
	public User saveUser(@RequestBody @Validated User user){
		return userDAO.save(user);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
	public void deleteUser(@PathVariable Long id){
		userDAO.delete(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody @Validated User user){
		User updateUs = userDAO.findOne(id);
		updateUs.setName(user.getName());
		updateUs.setSurename(user.getSurename());
		return userDAO.save(user);
	}
}
