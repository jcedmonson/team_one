package com.bah.msd.projectauthservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bah.msd.projectauthservice.jwt.JWTHelper;
import com.bah.msd.projectauthservice.jwt.JWTUtil;
import com.bah.msd.projectauthservice.jwt.Token;
import com.bah.msd.projectauthservice.jwt.TokenRequestData;
import com.bah.msd.projectauthservice.jwt.TokenResponseData;

@RestController
@RequestMapping("/token")
public class TokenApi {

	JWTUtil jwtUtil = new JWTHelper();
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> getToken(@RequestBody TokenRequestData tokenRequestData) {	
			
		RestTemplate restTemplate = new RestTemplate();
		TokenResponseData result = restTemplate.postForObject("http://localhost:8080/api/customers/authenticate", tokenRequestData, TokenResponseData.class);
		
		if (result.isAuthenticated()) {
			Token token = jwtUtil.createToken(result.getScopes());
			
			return ResponseEntity.ok(token);
		}
		
		return ResponseEntity.status(401).build();
	}
}
