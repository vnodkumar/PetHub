package com.vk.PetHub.controller;

import com.vk.PetHub.dto.UserCreateRequest;
import com.vk.PetHub.dto.UserUpdateRequest;
import com.vk.PetHub.dto.UserResponse;
import com.vk.PetHub.model.User;
import com.vk.PetHub.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //TO BE REMOVED, jus to work with users
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @Positive(message = "User Id must be positive") Long id){
        UserResponse resp = userService.getUserById(id);
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/api/users")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserCreateRequest request){
        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable @Positive(message = "User Id should be positive") Long id,@Valid @RequestBody UserUpdateRequest request){
        userService.updateUser(id,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
