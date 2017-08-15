package com.qxcmp.framework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface CodeRepository extends JpaRepository<Code, String>, JpaSpecificationExecutor<Code> {
}
