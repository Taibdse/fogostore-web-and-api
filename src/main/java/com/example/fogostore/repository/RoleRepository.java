package com.example.fogostore.repository;


import com.example.fogostore.common.enumeration.RoleName;
import com.example.fogostore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByNameEquals(RoleName roleName);
}
