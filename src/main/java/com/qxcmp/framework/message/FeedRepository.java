package com.qxcmp.framework.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface FeedRepository extends JpaRepository<Feed, Long>, JpaSpecificationExecutor<Feed> {
}
