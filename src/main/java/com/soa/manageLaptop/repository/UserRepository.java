package com.soa.manageLaptop.repository;

import com.soa.manageLaptop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.soa.manageLaptop.model.User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
