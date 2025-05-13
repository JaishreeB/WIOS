package com.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	@NotBlank
	@Size(min = 3, max = 20, message = "user name should be in the range of 3-20")
	private String name;
	private String email;
	@NotBlank
	@Size(min = 8, message = "minimum length of the password should be 8")
	private String password;
	@NotBlank(message="role should be either  user or admin")
	@Pattern(regexp = "^(USER|ADMIN)$", message = "Value must be either 'USER' or 'ADMIN'")
	private String roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
