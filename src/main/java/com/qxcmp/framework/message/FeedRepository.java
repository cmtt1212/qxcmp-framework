package com.qxcmp.framework.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
interface FeedRepository extends JpaRepository<Feed, Long>, JpaSpecificationExecutor<Feed> {

    /**
     * 查询用户指定日期之后的Feed流
     *
     * @param owner       用户ID
     * @param dateCreated 日期
     * @return 指定日期之后的Feed列表
     */
    List<Feed> findByOwnerAndDateCreatedAfterOrderByDateCreatedDesc(String owner, Date dateCreated);
}
