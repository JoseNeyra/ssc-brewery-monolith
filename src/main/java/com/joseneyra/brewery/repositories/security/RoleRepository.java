package com.joseneyra.brewery.repositories.security;

import com.joseneyra.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Integer> {
}
