package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.CustomUser;
import com.example.demo.entity.User;

/**
* Custom implementation of Spring Security's UserDetailsService interface.
* Provides a way to load user-specific data during authentication.
*/

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	/**
     * Repository for accessing user data in the database.
     */
	@Autowired
	private UserRepository userRepository;
	
	/**
     * Loads user-specific data based on the provided username during authentication.
     *
     * @param username The username of the user being authenticated.
     * @return UserDetails object representing the user's authentication and authorization information.
     * @throws UsernameNotFoundException If the specified username is not found in the database.
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("NO USER");
		}
		return new CustomUser(user);
		
	}

}
