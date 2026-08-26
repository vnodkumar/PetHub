package com.vk.PetHub.service;

import com.vk.PetHub.dto.UserCreateRequest;
import com.vk.PetHub.dto.UserUpdateRequest;
import com.vk.PetHub.dto.UserResponse;
import com.vk.PetHub.exception.UserAlreadyExistsException;
import com.vk.PetHub.exception.UserNotFoundException;
import com.vk.PetHub.model.User;
import com.vk.PetHub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo,PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public void createUser(UserCreateRequest request) {


        //check if user exist with email_id or phone
        if(userRepo.existsByEmail(request.email())||request.phone()!=null&&userRepo.existsByPhone(request.phone())){
            throw new UserAlreadyExistsException("User Already Exist!");
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(request.email().trim());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setAddress(StringUtils.hasText(request.address())? request.address().trim() : null);
        user.setPhone(StringUtils.hasText(request.phone())? request.phone().trim() : null);
        userRepo.save(user);
    }

    public UserResponse getUserById(Long id) {

        User user =  userRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getCreatedAt()
        );
    }

    public void updateUser(Long id, UserUpdateRequest request) {
        User user =  userRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));

        user.setName(request.name().trim());
        user.setAddress(request.address().trim());
        user.setPhone(request.phone().trim());

        userRepo.save(user);
    }

    public User getUserEntityById(Long id) {
        return userRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }
}
