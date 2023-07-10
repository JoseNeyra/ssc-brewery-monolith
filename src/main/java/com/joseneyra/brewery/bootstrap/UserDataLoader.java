package com.joseneyra.brewery.bootstrap;

import com.joseneyra.brewery.domain.security.Authority;
import com.joseneyra.brewery.domain.security.User;
import com.joseneyra.brewery.repositories.security.AuthorityRepository;
import com.joseneyra.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private static final String ADMIN_ROLE = "admin";
    private static final String USER_ROLE = "user";
    private static final String SCOTT_ROLE = "scott";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0) {
            loadUserData();
        }
    }

    @Transactional
    private void loadUserData() {
        Authority adminAuth = Authority.builder().role(ADMIN_ROLE).build();
        authorityRepository.save(adminAuth);

        Authority userAuth = Authority.builder().role(USER_ROLE).build();
        authorityRepository.save(userAuth);

        Authority scottAuth = Authority.builder().role(SCOTT_ROLE).build();
        authorityRepository.save(scottAuth);

        User admin = User.builder()
                .username("admin")
                .password(textEncoder("pass"))
                .authorities(new HashSet<>(Collections.singleton(adminAuth)))
                .build();
        userRepository.saveAndFlush(admin);

        User user = User.builder()
                .username("user")
                .password(textEncoder("pass"))
                .authorities(new HashSet<>(Collections.singleton(userAuth)))
                .build();
        userRepository.saveAndFlush(user);

        User scott = User.builder()
                .username("scott")
                .password(textEncoder("tiger"))
                .authorities(new HashSet<>(Collections.singleton(scottAuth)))
                .build();
        userRepository.saveAndFlush(scott);

    }

    private String textEncoder(String text) {
        return passwordEncoder.encode(text);
    }
}
