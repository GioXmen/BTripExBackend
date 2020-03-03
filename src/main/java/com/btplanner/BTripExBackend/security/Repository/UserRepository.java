package com.btplanner.BTripExBackend.security.Repository;


import com.btplanner.BTripExBackend.security.AccountModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
