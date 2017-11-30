package com.qxcmp.framework.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface InnerMessageRepository extends JpaRepository<InnerMessage, Long>, JpaSpecificationExecutor<InnerMessage> {

    @Query("select message from InnerMessage message where message.userID = :userID")
    Page<InnerMessage> findByUserID(@Param("userID") String userID, Pageable pageable);

}
