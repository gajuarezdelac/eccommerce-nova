package com.course.altanto.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
    
    User findUserByTokenAndUsername(String token, String username);
  
}
