package com.qxcmp.framework.account.username;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, String>, JpaSpecificationExecutor<SecurityQuestion> {

    Optional<SecurityQuestion> findByUserId(String userId);
}
