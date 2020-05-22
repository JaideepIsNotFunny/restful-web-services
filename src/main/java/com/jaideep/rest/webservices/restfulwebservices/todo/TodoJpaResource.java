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
public class TodoJpaResource {
	@Autowired
	private TodoHardCodedService todoService;
	
	@Autowired
	private TodoJpaRepository todoJpaRepository;
	
	@GetMapping("/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		return todoJpaRepository.findByUsername(username);
		//return todoService.findAll();
	}
	
	@GetMapping("/jpa/users/{username}/todos/{todo_id}")
	public Todo getTodo(@PathVariable String username,  @PathVariable long todo_id){
		return (Todo) todoJpaRepository.findById(todo_id).get();
		//return todoService.findById(todo_id);
	}
	
	//DELETE /users/{user_name}/todos/{todo_id}
	
	@DeleteMapping("/jpa/users/{username}/todos/{todo_id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long todo_id){
		todoJpaRepository.deleteById(todo_id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/jpa/users/{username}/todos/{todo_id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long todo_id, @RequestBody Todo todo){
		todo.setUsername(username);
		Todo savedtodo = todoJpaRepository.save(todo);
		return  new ResponseEntity<Todo>(todo,HttpStatus.OK); // we can simply return todo also, but using ResponseEntity it is easier to add functionality in future
	}
	
	@PostMapping("/jpa/users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username,  @RequestBody Todo todo){ // ye pura hi samajh nahi aya
		todo.setUsername(username);
		Todo createdTodo = todoJpaRepository.save(todo);
		
		//location
		//Get current resource url
		//append /{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		return  ResponseEntity.created(uri).build(); 	// returns a status of created and also the uri of the newly created object
	}
}
