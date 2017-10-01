package com.qxcmp.framework.mall;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface CommodityRepository extends JpaRepository<Commodity, Long>, JpaSpecificationExecutor<Commodity> {

    /**
     * 查询店铺拥有的商品
     *
     * @param store
     * @param pageable
     * @return
     */
    Page<Commodity> findByStore(Store store, Pageable pageable);

}
