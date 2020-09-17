package org.ray.flamingo.repository;


import org.ray.flamingo.security.User;

public interface UserRepository extends BaseRepository<User, Long>{
	
	User findByUsername(String username);
	
	boolean existsByUsername(String username);
	
}
