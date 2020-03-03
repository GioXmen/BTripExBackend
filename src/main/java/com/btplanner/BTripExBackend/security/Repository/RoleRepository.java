package com.btplanner.BTripExBackend.security.Repository;

import com.btplanner.BTripExBackend.security.AccountModel.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
