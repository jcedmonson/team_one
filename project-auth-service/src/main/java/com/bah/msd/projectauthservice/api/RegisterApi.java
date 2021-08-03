package com.bah.msd.projectauthservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bah.msd.projectauthservice.jwt.JWTHelper;
import com.bah.msd.projectauthservice.jwt.JWTUtil;
import com.bah.msd.projectauthservice.jwt.Token;

@RestController
@RequestMapping("/register")
public class RegisterApi {
	
	JWTUtil jwtUtil = new JWTHelper();
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody Object newCustomer) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> result = restTemplate.postForEntity("http://localhost:8080/api/customers", newCustomer, ResponseEntity.class);
		
		if (result.getStatusCode() == HttpStatus.CREATED) {
			Token token = jwtUtil.createToken("com.bah.msd.projectdataservice");
			
			return ResponseEntity.ok(token);
		}
		
		return ResponseEntity.badRequest().build();
	}
}