package com.qxcmp.framework.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface StationMessageRepository extends JpaRepository<StationMessage, Long>, JpaSpecificationExecutor<StationMessage> {

    @Query("select message from StationMessage message where message.userID = :userID")
    Optional<Page> findByUserID(@Param("userID") String userID, Pageable pageable);

}
