package com.joseneyra.brewery.security;

import com.joseneyra.brewery.domain.security.Authority;
import com.joseneyra.brewery.domain.security.User;
import com.joseneyra.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Converts our User Object to the Spring UserDetails Implementation, this is no longer needed because we have our own implementation now
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(),
//                user.getAccountNonLocked(), convertToSpringAuthorities(user.getAuthorities()));
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name: " + username +  " not found"));
    }

    // No longer needed
//    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
//        if (authorities != null && authorities.size() > 0) {
//            return authorities.stream()
//                    .map(Authority::getPermission)
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toSet());
//        } else {
//            return new HashSet<>();
//        }
//    }
}
