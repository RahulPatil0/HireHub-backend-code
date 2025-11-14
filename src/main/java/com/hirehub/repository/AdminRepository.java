package com.hirehub.repository;

import com.hirehub.model.Role;
import com.hirehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    List<User> findByRole(Role role);
}
