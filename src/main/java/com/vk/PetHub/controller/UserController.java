package com.vk.PetHub.controller;

import com.vk.PetHub.dto.UserCreateRequest;
import com.vk.PetHub.dto.UserUpdateRequest;
import com.vk.PetHub.dto.UserResponse;
import com.vk.PetHub.model.User;
import com.vk.PetHub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    //TO BE REMOVED, jus to work with users
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse resp = userService.getUserById(id);
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/api/users")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserCreateRequest request){
        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        userService.updateUser(id,request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
