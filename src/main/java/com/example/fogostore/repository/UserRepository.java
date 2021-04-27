package com.example.fogostore.repository;


import com.example.fogostore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameEquals(String username);
}
