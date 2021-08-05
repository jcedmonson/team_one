package com.bah.msd.projectauthservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bah.msd.projectauthservice.jwt.JWTHelper;
import com.bah.msd.projectauthservice.jwt.JWTUtil;
import com.bah.msd.projectauthservice.token.Token;
import com.bah.msd.projectauthservice.token.TokenRequestData;

@RestController
@RequestMapping("/token")
public class TokenApi {

	private static final String authEndpoint = "http://localhost:8080/api/customers/authenticate";

	JWTUtil jwtUtil = new JWTHelper();

	private String api_scope = "com.bah.msd.projectdataservice";

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> getToken(@RequestBody TokenRequestData tokenRequestData) {

		ResponseEntity<?> result = new RestTemplate().postForEntity(authEndpoint, tokenRequestData,
				String.class);

		if (result.getBody().equals("SUCCESS")) {
			Token token = jwtUtil.createToken(api_scope);

			return ResponseEntity.ok(token);
		}

		return ResponseEntity.status(401).build();
	}
}
