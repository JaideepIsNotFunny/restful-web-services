package com.jaideep.rest.basic.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurationBasicAuth extends WebSecurityConfigurerAdapter { //Configuration - file contains configuration,  EnableWebSecurity - it contains security
	/**
	 * Override this method to configure the {@link HttpSecurity}. Typically subclasses
	 * should not invoke this method by calling super as it may override their
	 * configuration. The default configuration is:
	 *
	 * <pre>
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 * </pre>
	 *
	 * @param http the {@link HttpSecurity} to modify
	 * @throws Exception if an error occurs
	 */
	// @formatter:off
	protected void configure(HttpSecurity http) throws Exception {
		//logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http
		.csrf().disable()
		
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.anyRequest().authenticated()
				.and()
			//.formLogin().and()
			.httpBasic();
	}
}
