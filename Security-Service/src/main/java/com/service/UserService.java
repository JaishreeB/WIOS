package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.UserInfo;
import com.exceptions.UserRoleNotFound;
import com.repository.UserInfoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	@Autowired
	private UserInfoRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String addUser(UserInfo userInfo) {
		String name = userInfo.getName();
		UserInfo obj1 = repository.findByName(name).orElse(null);
		if (obj1 == null) {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			repository.save(userInfo);
			return "Registration Successfully ";
		} else {
			return "This UserName is Already Registered.";
		}
	}

	public String getRoles(String username) {
		UserInfo obj2 = repository.findByName(username).orElse(null);
		if (obj2 != null) {
			return obj2.getRoles();
		}
		return "Not Found";
	}

	public String removeUser(int id) {
		repository.deleteById(id);
		return "User Deleted!!!";
	}

	public UserInfo getUserById(int userId) throws UserRoleNotFound {
		Optional<UserInfo> optional = repository.findById(userId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new UserRoleNotFound("User is not Found........");
	}

	public List<UserInfo> getAllUsers() {
		return repository.findAll();
	}

	public int getUserId(String username) {
		UserInfo obj2 = repository.findByName(username).orElse(null);
		if (obj2 != null) {
			return obj2.getId();
		}
		return 0;
	}

public List<String> getAllAdminEmails() {
	List<UserInfo> admins = repository.findByRoles("ADMIN");
	return admins.stream()
	.map(UserInfo::getEmail)
	.collect(Collectors.toList());
}

}
