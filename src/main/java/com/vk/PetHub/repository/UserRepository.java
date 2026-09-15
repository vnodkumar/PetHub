package com.vk.PetHub.repository;

import com.vk.PetHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //user exists with email
    boolean existsByEmail(String email);
    //user exists with phone
    boolean existsByPhone(String phone);

    User findByEmail(String email);
}
