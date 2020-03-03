package com.btplanner.BTripExBackend.security.Repository;


import com.btplanner.BTripExBackend.security.AccountModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.username = ?1, u.password = ?2 where u.id = ?3")
    void setUserInfoById(String username, String password, Long userId);
}
