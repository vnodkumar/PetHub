package com.vk.PetHub.controller;

import com.vk.PetHub.dto.AuthResponse;
import com.vk.PetHub.dto.CredentialDto;
import com.vk.PetHub.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public final AuthenticationManager authManager;
    public final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService){
        this.authManager=authManager;
        this.jwtService=jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> logIn(@RequestBody CredentialDto credential){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(credential.email(),credential.password()));

        String token = jwtService.generateToken(credential.email());
        AuthResponse response = new AuthResponse(credential.email(), token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
