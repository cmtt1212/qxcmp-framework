package com.qxcmp.framework.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface DepositOrderRepository extends JpaRepository<DepositOrder, String>, JpaSpecificationExecutor<DepositOrder> {
}
