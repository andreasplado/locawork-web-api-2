package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UserAuthRepository extends JpaRepository<UserEntity, String> {

    @Query(value="SELECT CASE WHEN COUNT(s)> 0 then true else false end FROM users u WHERE u.email=?1 AND u.password=?2", nativeQuery = true)
    boolean isAuthenticated(@Param("username") String username, @Param("password") String password);

    @Query(value="SELECT * FROM users u WHERE u.username=?1", nativeQuery = true)
    UserEntity existsByName(@Param("username") String username);
}
