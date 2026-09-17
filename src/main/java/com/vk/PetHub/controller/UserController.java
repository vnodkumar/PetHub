package com.vk.PetHub.controller;

import com.vk.PetHub.dto.UserCreateRequest;
import com.vk.PetHub.dto.UserUpdateRequest;
import com.vk.PetHub.dto.UserResponse;
import com.vk.PetHub.model.CustomUserDetails;
import com.vk.PetHub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //TO BE REMOVED, jus to work with users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){
        return new ResponseEntity<List<UserResponse>>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserById(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long id = userDetails.getId();
        UserResponse resp = userService.getUserById(id);
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserCreateRequest request){
        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,@Valid @RequestBody UserUpdateRequest request){
        Long id = userDetails.getId();
        userService.updateUser(id,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
