package com.phaiecobyte.spring_security.repository;

import com.phaiecobyte.spring_security.entity.Role;
import com.phaiecobyte.spring_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
}
