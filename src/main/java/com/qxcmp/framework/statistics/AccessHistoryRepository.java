package com.qxcmp.framework.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface AccessHistoryRepository extends JpaRepository<AccessHistory, Long>, JpaSpecificationExecutor<AccessHistory> {
}
