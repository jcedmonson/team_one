package com.bah.msd.projectauthservice.api;

import com.bah.msd.projectauthservice.jwt.*;
import com.bah.msd.projectauthservice.token.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// API for token generation
@RestController
@RequestMapping("/token")
public class TokenApi {
    JWTUtil jwtUtil = new JWTHelper();

    //takes post body
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> getToken(@RequestBody TokenRequestData tokenRequestData){

        // get info from tokenRequestData class
        String username = tokenRequestData.getUsername();
        String password = tokenRequestData.getPassword();
        String scopes = tokenRequestData.getScopes();

        // checking username and password exists and greater than 0
        // if yes, return 200 w/response object containing token
        if (username != null && username.length() > 0
            && password != null && password.length() > 0){
                    Token token = jwtUtil.createToken(scopes);
                    ResponseEntity<?> response = ResponseEntity.ok(token);
                    return response;
                }
        // who are you, 401 fam.
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}