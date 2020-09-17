package org.ray.flamingo.service;

import org.ray.flamingo.form.UserForm;
import org.ray.flamingo.repository.NodeRepository;
import org.ray.flamingo.repository.UserRepository;
import org.ray.flamingo.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired 
	private PollutionService pollServ;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private NodeRepository nodeRepo;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepo.findByUsername(username);
		
		if(user != null)
			return user;

		throw new UsernameNotFoundException("User '" + username + "' not found");
	}
	
	public boolean tryRegister(UserForm form) {
		String name = form.getUsername();
		
		if(pollServ.isClear(name))
			if(userRepo.existsByUsername(name) == false){
				User newbie = userRepo.save(form.toUser(passwordEncoder));
				nodeRepo.insertWithGivenId(newbie.getId(), name);
				return true;
			}
		
		return false;
	}

}