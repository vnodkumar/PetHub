package com.vk.PetHub.controller;

import com.vk.PetHub.dto.CredentialDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public final AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager){
        this.authManager=authManager;
    }


    @GetMapping("/login")
    public String logIn(@RequestBody CredentialDto credential){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(credential.email(),credential.password()));

        if(authentication.isAuthenticated())
            return "success";

        return "";
    }
}
