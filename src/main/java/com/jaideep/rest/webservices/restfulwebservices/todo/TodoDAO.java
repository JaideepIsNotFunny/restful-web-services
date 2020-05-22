package com.jaideep.rest.webservices.restfulwebservices.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component("todoDAO")
public class TodoDAO {
	public class todoRowMapper implements RowMapper<Todo>{
		@Override
		public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
			Todo todo = new Todo();
			todo.setId(rs.getLong("id"));
			todo.setDescription(rs.getString("description"));
			todo.setUsername(rs.getString("username"));
			todo.setTargetDate(rs.getDate("target_date"));
			todo.setDone(rs.getBoolean("done"));
			return todo;
		}
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(DataSource myBasicDataSource) {
		this.jdbcTemplate = new JdbcTemplate(myBasicDataSource);
	}

	private JdbcTemplate jdbcTemplate;
	private static List<Todo> todos = new ArrayList<>();
	private static long idCounter = 0;
	
	static {
		todos.add(new Todo(++idCounter,false,new Date(),"Jaideep","Read Earthsea"));
		todos.add(new Todo(++idCounter,false,new Date(),"Jaideep","Learn React"));
		todos.add(new Todo(++idCounter,false,new Date(),"Jaideep","Draw best sketch"));
	}
	
	public List<Todo> findAll(){
		List<Todo> todos1 = jdbcTemplate.query("select * from todo", new todoRowMapper());
		System.out.println(todos1);
		return todos1;
		
	}
	
	public List<Todo> findByUsername(String username){
		List<Todo> todos1 = jdbcTemplate.query("select * from todo where username = ?",new Object[] {username},  new todoRowMapper());
		System.out.println(todos1);
		return todos1;
	}
	
	
	public Todo save(Todo todo) {
		try {
			if(todo.getId() ==-1 || todo.getId() ==0) {
				jdbcTemplate.update( "INSERT INTO todo (username, description, target_date, done) VALUES (?, ?, ?, ?)", 
						todo.getUsername(), todo.getDescription(), todo.getTargetDate(), todo.getDone());
			}
			else {
				jdbcTemplate.update( "update todo set username = ?, description = ?, target_date = ?, done = ? where id = ?", 
						todo.getUsername(), todo.getDescription(), todo.getTargetDate(), todo.getDone(), todo.getId());
				
			}
		}
		catch( DataAccessException e) {
			return null;
		}
		return todo;
	}
	
	public boolean deleteById(long id) {
		try {
			jdbcTemplate.update( "delete from todo where id = ?", id);
			return true;
		}
		catch(DataAccessException e) {
			return false;
		}
		/*Todo todo = findById(id);
		if(todo == null)
			return null;
		if(todos.remove(todo)) {
			return todo;
		}
		return null;*/
	}
	
	public Todo findOneTodo(String username,long id) {
		try {
			List <Todo>todos1 =  jdbcTemplate.query("select * from todo where username = ? and id = ?",new Object[]{username,id}, new todoRowMapper());
			System.out.println(todos1);
			return todos1.get(0);
		}
		catch(DataAccessException e) {
			return null;
		}
	}

	/*public Todo findById(long id) {
		for(Todo todo:todos) {
			if(todo.getId() == id)
				return todo;
		}
		return null;
	}*/

}
