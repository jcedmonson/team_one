package com.bah.msd.projectdataservice.jwt;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bah.msd.projectdataservice.token.Token;

public class JWTHelper implements JWTUtil {
	
		@Override
		public Token createToken(String scopes) {
			
			try {
			    Algorithm algorithm = Algorithm.HMAC256("secret");
			    long fiveHoursInMillis = 1000 * 60 * 60 * 5;
			    Date expireDate = new Date(System.currentTimeMillis() + fiveHoursInMillis);
			    String token = JWT.create()
			    	.withSubject("apiuser")
			        .withIssuer("me@me.com")
			        .withClaim("scopes", scopes)
			        .withExpiresAt(expireDate)
			        .sign(algorithm);
			    return new Token(token);
			} catch (JWTCreationException exception){
				return null;
			}	
		}

		@Override
		public boolean verifyToken(String token) {

			try {
			    Algorithm algorithm = Algorithm.HMAC256("secret");
			    JWTVerifier verifier = JWT.require(algorithm)
			        .withIssuer("me@me.com")
			        .build(); 
			    DecodedJWT jwt = verifier.verify(token);
			    return true;
			} catch (JWTVerificationException exception){
				return false;
			}		
			
		}

		public Map<String, Claim> getClaims(String token) {
			try {
			    Algorithm algorithm = Algorithm.HMAC256("secret");
			    JWTVerifier verifier = JWT.require(algorithm)
			        .withIssuer("me@me.com")
			        .build(); //Reusable verifier instance
			    DecodedJWT jwt = verifier.verify(token);
			    return jwt.getClaims();
			} catch (JWTVerificationException exception){
				return null;
			}
		}
		
		@Override
		public String getScopes(String token) {
			try {
			    Algorithm algorithm = Algorithm.HMAC256("secret");
			    JWTVerifier verifier = JWT.require(algorithm)
			        .withIssuer("me@me.com")
			        .build(); //Reusable verifier instance
			    DecodedJWT jwt = verifier.verify(token);
			    return jwt.getClaim("scopes").asString();
			} catch (JWTVerificationException exception){
				return null;
			}
		}
	}
