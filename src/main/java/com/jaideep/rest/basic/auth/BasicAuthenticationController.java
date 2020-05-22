package com.jaideep.rest.basic.auth;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins= "http://localhost:4200")
public class BasicAuthenticationController {
	AuthenticationBean helloWorldBean;
	
	public AuthenticationBean getHelloWorldBean() {
		return helloWorldBean;
	}

	public void setHelloWorldBean(AuthenticationBean helloWorldBean) {
		this.helloWorldBean = helloWorldBean;
	}

	
	
	@GetMapping(path="/basicauth")
	public AuthenticationBean helloWorldUsingBean() {
		return new AuthenticationBean("You are authenticated");
	}
	
	
}
