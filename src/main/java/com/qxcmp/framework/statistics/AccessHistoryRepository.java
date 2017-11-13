package com.qxcmp.framework.statistics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Repository
interface AccessHistoryRepository extends JpaRepository<AccessHistory, Long>, JpaSpecificationExecutor<AccessHistory> {

    @Query("select a.url as url, count(a) as nbr from AccessHistory a where a.dateCreated > :date group by a.url order by nbr desc ")
    Page<Map<String, Object>> findByDateCreatedAfter(@Param("date") Date date, Pageable pageable);
}
