package com.kjoseph.portfolioMS.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjoseph.portfolioMS.backend.model.User;
import com.kjoseph.portfolioMS.backend.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable(value="id") Long id) {
		User user = userRepository.getUserById(id);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value="id") Long id, @Valid @RequestBody User user) {
		User updatedUser = userRepository.getUserById(id);
		updatedUser.setUsername(user.getUsername());
		updatedUser.setPassword(user.getPassword());
		updatedUser.setRole(user.getRole());
		updatedUser.setEnabled(user.isEnabled());
		updatedUser = userRepository.save(updatedUser);
		return ResponseEntity.ok().body(updatedUser);
	}
	
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value="id") Long id) {
		User user = userRepository.getUserById(id);
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap <>();
		response.put("deleted", true);
		return response;
	}
	
}
