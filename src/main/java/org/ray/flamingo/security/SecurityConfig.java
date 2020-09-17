package org.ray.flamingo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder(){
		return new Pbkdf2PasswordEncoder("53cr3t");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/", "/login", "/register").access("permitAll")
			.antMatchers("/node/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/schedule/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/library/admin/**").access("hasRole('ROLE_ADMIN')")
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/node/admin/start")
			.and()
				.logout()
				.logoutSuccessUrl("/login")
			.and()
			    .csrf()
			    .ignoringAntMatchers("/h2-console/**")
			.and()
			    .headers()
			    .frameOptions()
			    .sameOrigin();
	}
	
}
