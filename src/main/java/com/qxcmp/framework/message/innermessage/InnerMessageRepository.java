package com.qxcmp.framework.message.innermessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 站内信
 */
@Repository
interface InnerMessageRepository extends JpaRepository<InnerMessage, String>, JpaSpecificationExecutor<InnerMessage>{

    Optional<InnerMessage> findByUserId(String userId);

}
