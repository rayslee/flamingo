package org.ray.flamingo.form;

import javax.validation.constraints.Size;

import org.ray.flamingo.security.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class UserForm {
	
	@Size(min=6, message="longer than six")
	private String username;
	
	@Size(min=6, message="longer than six")
	private String password;
	
	public User toUser(PasswordEncoder passwordEncoder) {
	    return new User(username, passwordEncoder.encode(password));
	}

}