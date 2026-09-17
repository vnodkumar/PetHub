package com.vk.PetHub.controller;

import com.vk.PetHub.dto.CredentialDto;
import com.vk.PetHub.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public final AuthenticationManager authManager;
    public final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService){
        this.authManager=authManager;
        this.jwtService=jwtService;
    }


    @GetMapping("/login")
    public String logIn(@RequestBody CredentialDto credential){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(credential.email(),credential.password()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(credential.email());

        return "";
    }
}
