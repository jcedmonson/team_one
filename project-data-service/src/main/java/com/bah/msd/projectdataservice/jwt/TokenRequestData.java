package com.bah.msd.projectdataservice.jwt;

public class TokenRequestData {
	String name;
	String password;
	
	public TokenRequestData() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
