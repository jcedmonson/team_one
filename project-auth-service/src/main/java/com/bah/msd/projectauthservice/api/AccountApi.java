package com.bah.msd.projectauthservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountApi {
	
	@GetMapping
	public ResponseEntity<?> checkStatus() {
		return ResponseEntity.ok().build();
	}
}
