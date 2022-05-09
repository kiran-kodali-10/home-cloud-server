package com.cloudforstorage.homecloudserver.controller;

import com.cloudforstorage.homecloudserver.bean.UserBean;
import com.cloudforstorage.homecloudserver.payload.request.LoginRequest;
import com.cloudforstorage.homecloudserver.payload.request.SignUpRequest;
import com.cloudforstorage.homecloudserver.payload.response.LoginResponse;
import com.cloudforstorage.homecloudserver.payload.response.SignUpResponse;
import com.cloudforstorage.homecloudserver.security.JwtUtils;
import com.cloudforstorage.homecloudserver.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(value = "http://localhost:3000")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     *
     * @param loginRequest
     * @return loginResponse that contains user details and JWT.
     *
     * Authenticates the user and generates JWT for that user.
     */
    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Started authentication for user: "+loginRequest.getUsername());
        if(loginRequest == null)
            return new ResponseEntity<>("Login request is null", HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(!authentication.isAuthenticated()){
            return ResponseEntity.ok("Username or pasword not correct"); // Change the status in future
        }
        String jwt = jwtUtils.generateJwt(authentication);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwt);
        return ResponseEntity.ok(loginResponse);
    }

    /**
     *
     * @param signUpRequest
     * @return SignUpResponse contains user details and JWT.
     * @throws Exception
     *
     * Registers the user
     * TO DO:
     *  1. Server side validation
     *
     */
    @PostMapping(value="/signup")
    public ResponseEntity registerUser( @RequestBody SignUpRequest signUpRequest) throws Exception{
        UserBean userBean = userService.registerUser(signUpRequest);
        SignUpResponse response = new SignUpResponse();
        response.setId(userBean.getId());
        response.setUsername(userBean.getUsername());
        response.setRoles(userBean.getRoles());
       return ResponseEntity.ok(response);
    }
}
