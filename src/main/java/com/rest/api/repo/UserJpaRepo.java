package com.rest.api.repo;

import com.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    Optional<User> findByUid(String uid);
    Optional<User> findByUidAndProvider(String uid, String provider);

    @Transactional
    void deleteByUid(String email);
}
