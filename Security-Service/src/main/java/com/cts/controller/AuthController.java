package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthRequest;
import com.entity.UserInfo;
import com.exceptions.UserRoleNotFound;
import com.repository.UserInfoRepository;
import com.service.JwtService;
import com.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/auth")
//@CrossOrigin("*")
@AllArgsConstructor
@NoArgsConstructor
public class AuthController {
	@Autowired
	private UserService service;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserInfoRepository repo;
	@Autowired
	private AuthenticationManager authenticationManager;

	// http://localhost:9090/auth/welcome
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	// http://localhost:9090/auth/new
	@PostMapping("/new")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	// http://localhost:9090/auth/authenticate
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws NullPointerException{
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
			return jwtService.generateToken(authRequest.getUsername(), obj.getRoles());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	
	@GetMapping("/getroles/{username}")
	public String getRoles(@PathVariable String username) {
		return service.getRoles(username);
	}
	@GetMapping("/getUserId/{username}")
	public int getUserId(@PathVariable String username) {
		return service.getUserId(username);
	}
	
	@GetMapping("/getAdmins")
	public List<String> getAllAdmins() {
		return service.getAllAdminEmails();
	}


	@GetMapping("/fetchById/{uid}")
	public UserInfo getUserById(@PathVariable("uid") int id) throws UserRoleNotFound {
		return service.getUserById(id);
	}

	@DeleteMapping("/deleteById/{uid}")
	public String removeUser(@PathVariable("uid") int id) {
		return service.removeUser(id);
	}

	@GetMapping("/fetchAll")
	public List<UserInfo> getAllUsers() {
		return service.getAllUsers();
	}
}
