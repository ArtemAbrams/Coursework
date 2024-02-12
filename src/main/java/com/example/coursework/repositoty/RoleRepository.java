package com.example.coursework.repositoty;

import com.example.coursework.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRole(String role);
}
