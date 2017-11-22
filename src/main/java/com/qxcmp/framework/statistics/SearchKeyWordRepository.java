package com.qxcmp.framework.statistics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
interface SearchKeyWordRepository extends JpaRepository<SearchKeyWord, Long>, JpaSpecificationExecutor<SearchKeyWord> {

    @Query("select new com.qxcmp.framework.statistics.SearchKeyWordPageResult(a.title, count(a)) from SearchKeyWord a where a.dateCreated > :date group by a.title order by count(a) desc")
    Page<SearchKeyWordPageResult> findByDateCreatedAfter(@Param("date") Date date, Pageable pageable);
}
