package com.bah.msd.projectdataservice.jwt;

import com.bah.msd.projectdataservice.token.Token;

public interface JWTUtil {
	public boolean verifyToken(String jwt_token);
	public String getScopes(String jwt_token) ;
	public Token createToken(String scopes) ;
}