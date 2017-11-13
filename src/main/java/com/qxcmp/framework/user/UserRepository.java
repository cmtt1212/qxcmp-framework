package com.qxcmp.framework.user;

import com.qxcmp.framework.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByOpenID(String openID);

    @Query("select user from User user join user.roles role inner join role.privileges privilege where privilege.name = :privilege")
    List<User> findByAuthority(@Param("privilege") String privilege);

    @Query("select user from User user inner join user.roles role where role = :role")
    List<User> findByRole(@Param("role") Role role);

    @Query("select user from User user where user.openID <> ''")
    Page<User> findWeixinUser(Pageable pageable);
}
