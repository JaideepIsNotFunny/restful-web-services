package com.jaideep.rest.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins= "http://localhost:4200")
public class HelloWorldController {
	HelloWorldBean helloWorldBean;
	
	public HelloWorldBean getHelloWorldBean() {
		return helloWorldBean;
	}

	public void setHelloWorldBean(HelloWorldBean helloWorldBean) {
		this.helloWorldBean = helloWorldBean;
	}

	//GET
	//URI - /hello-world
	//method - "HelloWorld"
	@RequestMapping(method = RequestMethod.GET, path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldUsingBean() {
		return new HelloWorldBean("Go Corona Go");
	}
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		//throw new RuntimeException("Something Went Wrong");
		return new HelloWorldBean(String.format("Hello ji, %s",name));
		
	}
	
}
