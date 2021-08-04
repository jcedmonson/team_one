package com.bah.msd.projectauthservice.jwt;

import com.bah.msd.projectauthservice.token.Token;

// intergace for JWT usage
public interface JWTUtil {
    public boolean verifyToken(String jwt_token);
    public String getScopes(String jwt_token);
    public Token createToken(String scopes);
}
