package com.jaideep.rest.webservices.restfulwebservices.todo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
@CrossOrigin(origins= "http://localhost:4200")
public class TodoDataResource {
	@Autowired
	private TodoDAO todoDAO;
	

	
	@GetMapping("/mysql/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		return todoDAO.findByUsername(username);
		
	}
	
	@GetMapping("/mysql/users/{username}/todos/{todo_id}")
	public Todo getTodo(@PathVariable String username,  @PathVariable long todo_id){
		
		return todoDAO.findOneTodo(username,todo_id);
	}
	
	//DELETE /users/{user_name}/todos/{todo_id}
	
	@DeleteMapping("/mysql/users/{username}/todos/{todo_id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long todo_id){
		
		if(todoDAO.deleteById(todo_id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/mysql/users/{username}/todos/{todo_id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long todo_id, @RequestBody Todo todo){
		Todo savedtodo = todoDAO.save(todo);
		return  new ResponseEntity<Todo>(todo,HttpStatus.OK); // we can simply return todo also, but using ResponseEntity it is easier to add functionality in future
	}
	
	@PostMapping("/mysql/users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username,  @RequestBody Todo todo){ // ye pura hi samajh nahi aya
		Todo createdTodo = todoDAO.save(todo);
		
		//location
		//Get current resource url
		//append /{id}
		if(createdTodo!=null) {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
				return  ResponseEntity.created(uri).build(); 	// returns a status of created and also the uri of the newly created object
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
}
