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

@RestController
@RequestMapping("/register")
public class RegisterApi {

	private static final String createCustomerEndpoint = "http://localhost:8080/api/customers";

	JWTUtil jwtUtil = new JWTHelper();

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> register(@RequestBody Object newCustomer) {

		ResponseEntity<?> result = new RestTemplate().postForEntity(createCustomerEndpoint, newCustomer,
				String.class);

		if (result.getStatusCode() == HttpStatus.CREATED) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
}