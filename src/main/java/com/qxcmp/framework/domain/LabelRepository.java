package com.qxcmp.framework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface LabelRepository extends JpaRepository<Label, Long>, JpaSpecificationExecutor<Label> {

    /**
     * @param type
     * @param name
     * @return
     */
    Optional<Label> findByTypeAndName(String type, String name);
}
