package com.hirehub.repository;

import com.hirehub.model.Role;
import com.hirehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role); // not used now, but ok to keep

    List<User> findByRole(Role role);

    List<User> findByDocumentsVerifiedFalse();
}
