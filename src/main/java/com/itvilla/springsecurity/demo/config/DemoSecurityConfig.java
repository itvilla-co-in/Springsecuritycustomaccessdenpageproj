package com.itvilla.springsecurity.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("naren").password("test123").roles("EMPLOYEE"))
			.withUser(users.username("donna").password("test123").roles("GUEST"))
			.withUser(users.username("amit").password("test123").roles("EMPLOYEE","MANAGER"))
			.withUser(users.username("tejas").password("test123").roles("EMPLOYEE","ADMIN"));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll()
		    .antMatchers("/employee/**").hasAnyRole("EMPLOYEE","MANAGER")
		    .antMatchers("/manager/**").hasRole("MANAGER")
		    .antMatchers("/admin/**").hasRole("ADMIN")
		    .and()
			.formLogin()
				.loginPage("/mylogin")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/accessden");
		
	}
	
	
	
}






