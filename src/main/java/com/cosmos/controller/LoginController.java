package com.cosmos.controller;

import com.cosmos.models.JWTRequest;
import com.cosmos.models.JWTResponse;
import com.cosmos.service.UserService;
import com.cosmos.utility.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private JWTUtil jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public String getHome(){
        return "Welcome Abhijit Mishra";
    }
    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest)throws Exception{
        try {


            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(),
                    jwtRequest.getPassword()
            ));
        }catch (BadCredentialsException ex){
            throw new Exception("Invalid Credentils");
        }

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtility.generateToken(userDetails);

        return new JWTResponse(token);
    }
}
