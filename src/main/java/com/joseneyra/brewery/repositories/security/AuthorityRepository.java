package com.joseneyra.brewery.repositories.security;

import com.joseneyra.brewery.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository <Authority, Integer> {
}
