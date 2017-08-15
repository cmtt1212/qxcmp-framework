package com.qxcmp.framework.domain;

import com.qxcmp.framework.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
interface ChannelRepository extends JpaRepository<Channel, Long>, JpaSpecificationExecutor<Channel> {

    Optional<Channel> findByAlias(String alias);

    List<Channel> findByOwner(User owner);

    @Query("select channel from Channel channel inner join channel.admins admin where admin = :user")
    List<Channel> findByAdminsContains(@Param("user") User user);

    @Query("select channel from Channel channel inner join channel.catalog catalog where catalog = :catalog")
    List<Channel> findByCatalog(@Param("catalog") String catalog);

    List<Channel> findByCatalogContains(Set<String> catalogs);
}
