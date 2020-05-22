package com.jaideep.rest.webservices.restfulwebservices.todo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.jaideep.rest.webservices.restfulwebservices.todo.Todo;

@Component
@Aspect
public class Log {
	
	@Before("execution(org.springframework.http.ResponseEntity com.jaideep.rest.webservices.restfulwebservices.todo.TodoDataResource.updateTodo(..))")
	public void updateTodoCalled(JoinPoint jp) {
		System.out.println("Update todo called, log from Aspect ;-)");
		System.out.println(jp.getKind());
		
		Object[]args = jp.getArgs();
		Todo savedtodo = ((Todo)args[0]); 
		System.out.println(savedtodo);
		
	}

}
