package com.bah.msd.projectdataservice.jwt;

public class TokenResponseData {
	String scopes;
	Boolean authenticated = false;

	public TokenResponseData() {
		// TODO Auto-generated constructor stub
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public Boolean isAuthenticated() {
		return authenticated;
	}
	
	public void setAuthenticated(Boolean auth) {
		this.authenticated = auth;
	}
}