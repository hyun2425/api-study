package com.rest.api.repo;

import com.rest.api.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
public class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void whenFindByUid_thenReturnUser() {
        String uid = "hsj@infotech.co.kr";
        String name = "hsj";

        // given
        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        // when
        Optional<User> user = userJpaRepo.findByUid(uid);

        // then
        assertThat(user).isNotNull();
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getName()).isEqualTo(name);
    }

}
