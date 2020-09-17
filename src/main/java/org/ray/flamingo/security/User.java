package org.ray.flamingo.security;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class User implements UserDetails {

	private static final long serialVersionUID = 4168672751255187168L;

	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	private Long id;
	
	@NotNull
	private final String username;
	
	@NotNull
	private final String password;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	@Override 
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override 
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override 
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override 
	public boolean isEnabled() {
		return true;
	}

}
