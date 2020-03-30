package com.btplanner.btripexbackend.datamodel.repository;


import com.btplanner.btripexbackend.datamodel.accountmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select new User(u.id, u.username, u.password) from User u where u.username = ?1 and u.password = ?2")
    User findByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    @Query("update User u set u.username = ?1, u.password = ?2 where u.id = ?3")
    void setUserInfoById(String username, String password, Long userId);

    @Modifying
    @Transactional
    @Query("update User u set u.password = ?2 where u.username = ?1")
    void updateUserPassword(String username, String password);
}
