package com.qxcmp.framework.mall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface StoreRepository extends JpaRepository<Store, String>, JpaSpecificationExecutor<Store> {
}
