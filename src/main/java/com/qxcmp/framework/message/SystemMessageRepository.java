package com.qxcmp.framework.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 站内信
 */
@Repository
interface SystemMessageRepository extends JpaRepository<SystemMessage, Long>, JpaSpecificationExecutor<SystemMessage>{

    Page<SystemMessage> findByUserId(String userId, Pageable pageable);

    Optional<SystemMessage> findByUserId(String userId);
}
