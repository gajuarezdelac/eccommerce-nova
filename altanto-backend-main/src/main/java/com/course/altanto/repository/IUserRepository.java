package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.altanto.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

}
