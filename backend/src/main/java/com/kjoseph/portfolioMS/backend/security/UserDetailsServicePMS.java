package com.kjoseph.portfolioMS.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kjoseph.portfolioMS.backend.model.User;
import com.kjoseph.portfolioMS.backend.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServicePMS implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return new UserDetailsPMS(user);
	}

}
