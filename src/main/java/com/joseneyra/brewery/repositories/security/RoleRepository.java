package com.joseneyra.brewery.repositories.security;

import com.joseneyra.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role, Integer> {
    Optional<Role> findByName(String role);
}
