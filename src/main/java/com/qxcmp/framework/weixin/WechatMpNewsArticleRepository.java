package com.qxcmp.framework.weixin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface WechatMpNewsArticleRepository extends JpaRepository<WechatMpNewsArticle, Long>, JpaSpecificationExecutor<WechatMpNewsArticle> {

    Optional<WechatMpNewsArticle> findByUrl(String url);

}
