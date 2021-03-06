package com.course.altanto.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    User findUserByUsername(String username);
    
    User findUserByTokenAndUsername(String token, String username);

    User findUserById(String id);
  
    @Query(value = "SELECT * FROM user u WHERE u.username LIKE %:username% AND u.names LIKE %:names% AND u.surnames LIKE %:surnames%",nativeQuery = true)
	Page<User> searchByFilters(@Param("username") String username,@Param("names") String names,@Param("surnames") String surnames, Pageable pageable);
	

}
