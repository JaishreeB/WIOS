package com.cts.wios.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="SECURITY-SERVICE",path="/auth")
public interface UserInfoClient {
	@GetMapping("/getAdmins")
	public List<String> getAllAdmins();

}
